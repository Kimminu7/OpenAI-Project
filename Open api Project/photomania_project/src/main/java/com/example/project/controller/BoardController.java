package com.example.project.controller;

import com.example.project.dto.*;
        import com.example.project.entity.Board;
import com.example.project.sec.FileStorageUtil;
import com.example.project.sec.MemberDetails;
import com.example.project.service.BoardLikeService;
import com.example.project.service.BoardService;
import com.example.project.service.CommentService;
import com.example.project.service.ReCommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
        import org.springframework.web.multipart.MultipartFile;
import com.example.project.sec.FileStorageUtil; // 추가
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSession;
import java.nio.file.Files; // 추가

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BoardController {

    // 컨트롤러 클래스 내에서 Logger 객체 선언
    private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
    private final BoardService boardService;
    private final CommentService commentService;
    private final ReCommentService reCommentService;
    private final BoardLikeService boardLikeService;
    private final FileStorageUtil fileStorageUtil;

    // 게시판 목록 페이지
    @GetMapping("/board")
    public String list(@RequestParam(value = "page", defaultValue = "1") int page,
                       @RequestParam(value = "size", defaultValue = "10") int size,
                       Model model) {
        // PageRequestDTO 생성
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(page)
                .size(size)
                .build();

        // 페이징 처리된 게시글 목록 조회
        PageResultDTO<BoardDTO, Board> result = boardService.getList(pageRequestDTO);

        // 모델에 추가
        model.addAttribute("boardList", result.getDtoList());
        model.addAttribute("pageResult", result);

        return "board"; // board.html로 연결
    }

    // 게시글 작성 페이지
    @GetMapping("/write")
    public String createForm(Model model) {
        log.info("게시글 작성 페이지로 이동");
        model.addAttribute("board", new BoardDTO());
        return "write"; // write.html로 연결
    }


    // 게시물 좋아요 처리
    @PostMapping("/board/{id}/like")
    public String like(@PathVariable Long id, @AuthenticationPrincipal MemberDetails memberDetails, Model model) {
        log.info("좋아요 처리: id={}", id);

        String memberEmail = memberDetails.getUsername();

        // 좋아요 토글 처리
        boolean likeStatus = boardLikeService.toggleLike(id, memberEmail);

        // 게시글 정보 다시 가져오기 (좋아요 상태를 반영)
        BoardDTO boardDTO = boardService.getBoardById(id);

        // 댓글 목록 조회 (대댓글 포함)
        List<CommentResponseDTO> comments = commentService.commentList(id);
        // 모델에 좋아요 상태와 게시글 정보, 댓글 목록 추가
        model.addAttribute("board", boardDTO);
        model.addAttribute("likeStatus", likeStatus);  // 좋아요 상태를 전달
        model.addAttribute("comments", comments);  // 댓글 목록을 전달

        return "detail";  // 리디렉션 없이 같은 페이지로 데이터를 갱신
    }

    // 게시물 작성 로직 수정
    @PostMapping("/board")
    public String create(@RequestParam String title,
                         @RequestParam String content,
                         @RequestParam(required = false) MultipartFile file,
                         Model model, @AuthenticationPrincipal MemberDetails memberDetails) {
        log.info("게시글 작성 처리: 제목={}, 내용={}", title, content);

        try {
            String filename = null;

            // 파일 저장 처리
            if (file != null && !file.isEmpty()) {
                log.info("파일이 존재하므로 저장을 시작합니다.");
                filename = fileStorageUtil.saveFile(file);
            } else {
                log.info("파일이 존재하지 않거나 비어 있습니다.");
            }

            // BoardDTO 생성
            BoardDTO boardDTO = BoardDTO.builder()
                    .title(title)
                    .content(content)
                    .filename(filename) // 저장된 파일 이름 설정
                    .email(memberDetails.getUsername())
                    .views(0)
                    .likes(0)
                    .build();

            log.info("게시글 DTO 생성: {}", boardDTO);

            // 게시글 저장
            boardService.saveBoard(boardDTO);
            return "redirect:/board"; // 게시판 목록으로 리디렉션
        } catch (Exception e) {
            log.error("게시글 작성 중 오류 발생", e);
            model.addAttribute("error", "게시글 저장 중 오류가 발생했습니다.");
            return "write";
        }
    }


    // 게시글 상세 조회
    @GetMapping("/board/{id}")
    public String detail(@PathVariable Long id, HttpSession session, Model model) {
        boardService.incrementViews(id); // 조회수 증가
        BoardDTO boardDTO = boardService.getBoardById(id); // 게시글 조회

        // 로그인한 사용자 정보 가져오기
        MemberDTO loginMember = (MemberDTO) session.getAttribute("member");

        // 로그 출력 (콘솔에서 확인)
        logger.info("로그인한 사용자 정보: {}", loginMember); // 로그인한 사용자 정보 출력
        logger.info("게시글 작성자 정보: {}", boardDTO.getMember()); // 게시글 작성자 정보 출력

        // 게시물 작성자 이메일 추가
        model.addAttribute("board", boardDTO);
        model.addAttribute("boardOwnerEmail", boardDTO.getEmail()); // 게시글 작성자 이메일
        model.addAttribute("loginUserEmail", (loginMember != null) ? loginMember.getEmail() : null); // 로그인 사용자 이메일

        // 댓글 목록 조회
        List<CommentResponseDTO> comments = commentService.commentList(id);
        model.addAttribute("comments", comments);

        return "detail";
    }


    // 게시글 수정 페이지
    @GetMapping("/board/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        log.info("게시글 수정 페이지로 이동: id={}", id);
        BoardDTO boardDTO = boardService.getBoardById(id);
        model.addAttribute("board", boardDTO);
        return "/edit"; //
    }
    // 게시글 수정 처리
    @PostMapping("/board/{id}/edit")
    public String update(@PathVariable Long id,
                         @ModelAttribute BoardDTO boardDTO,
                         @RequestParam(required = false) MultipartFile file,
                         Model model, @AuthenticationPrincipal MemberDetails memberDetails) {
        log.info("게시글 수정 처리: id={}, {}", id, boardDTO);

        try {
            String filename = boardDTO.getFilename(); // 기존 파일명

            // 새로운 파일이 업로드 되면 파일 저장 처리
            if (file != null && !file.isEmpty()) {
                log.info("새로운 파일이 존재하므로 저장을 시작합니다.");
                filename = fileStorageUtil.saveFile(file);
            }

            // 게시글 수정 DTO에 파일명 추가
            boardDTO.setFilename(filename);
            //작성자 추가
            boardDTO.setEmail(memberDetails.getUsername());
            boardService.saveBoard(boardDTO); // 수정된 게시글 저장

            return "redirect:/board/" + id; // 수정된 게시글 상세로 리디렉션
        } catch (Exception e) {
            log.error("게시글 수정 중 오류 발생", e);
            model.addAttribute("error", "게시글 수정 중 오류가 발생했습니다.");
            return "board/form"; // 수정 폼으로 돌아가기
        }
    }

    // 게시글 삭제 처리
    @PostMapping("/board/{id}/delete")
    public String deleteBoard(@PathVariable Long id, HttpSession session) {
        MemberDTO loginMember = (MemberDTO) session.getAttribute("member");
        BoardDTO boardDTO = boardService.getBoardById(id);

        if (loginMember != null && loginMember.getEmail().equals(boardDTO.getEmail())) {
            boardService.deleteBoard(id);
            return "redirect:/board"; // 삭제 후 게시판 목록으로 이동
        } else {
            return "redirect:/board/" + id; // 권한이 없을 경우 다시 상세 페이지로 이동
        }
    }

}
