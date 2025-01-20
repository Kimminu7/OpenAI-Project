package com.example.project.controller;

import com.example.project.dto.*;
import com.example.project.entity.Board;
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

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final CommentService commentService;
    private final ReCommentService reCommentService;
    private final BoardLikeService boardLikeService;

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
        for (CommentResponseDTO comment : comments) {
            List<ReCommentResponseDTO> replies = reCommentService.getReComments(comment.getId());
            comment.setReplies(replies); // 댓글 객체에 대댓글 추가
        }

        // 모델에 좋아요 상태와 게시글 정보, 댓글 목록 추가
        model.addAttribute("board", boardDTO);
        model.addAttribute("likeStatus", likeStatus);  // 좋아요 상태를 전달
        model.addAttribute("comments", comments);  // 댓글 목록을 전달

        return "detail";  // 리디렉션 없이 같은 페이지로 데이터를 갱신
    }



    @PostMapping("/board")
    public String create(@RequestParam String title,
                         @RequestParam String content,
                         @RequestParam(required = false) MultipartFile file,
                         Model model, @AuthenticationPrincipal MemberDetails memberDetails) {
        log.info("게시글 작성 처리: 제목={}, 내용={}", title, content);

        try {
            // 파일 데이터를 처리
            byte[] fileData = null;
            String filename = null;
            if (file != null && !file.isEmpty()) {
                fileData = file.getBytes();
                filename = file.getOriginalFilename();
            }

            // BoardDTO 생성
            BoardDTO boardDTO = BoardDTO.builder()
                    .title(title)
                    .content(content)
                    .filename(filename)
                    .data(fileData)
                    .views(0)
                    .likes(0)
                    .email(memberDetails.getUsername())
                    .build();

            // 게시글 저장
            BoardDTO saveDTO = boardService.saveBoard(boardDTO);
            if (saveDTO != null) {
                model.addAttribute("message", "게시글이 성공적으로 작성되었습니다.");
                return "redirect:/board"; // 게시판 목록으로 리디렉션
            } else {
                model.addAttribute("error", "게시글 저장 중 오류가 발생했습니다.");
                return "write"; // 오류 발생 시 다시 작성 페이지로 이동
            }

        } catch (Exception e) {
            log.error("게시글 작성 중 오류 발생", e);
            model.addAttribute("error", "게시글 저장 중 오류가 발생했습니다.");
            return "write"; // 오류 발생 시 다시 작성 페이지로 이동
        }
    }

    // 게시글 상세 조회
    @GetMapping("/board/{id}")
    public String detail(@PathVariable Long id, Model model) {
        // 게시글 조회 및 조회수 증가
        boardService.incrementViews(id); // 조회수 증가

        // 게시글 상세 조회
        BoardDTO boardDTO = boardService.getBoardById(id);

        // 게시글 정보 전달
        model.addAttribute("board", boardDTO);

        // 댓글 목록 조회
        List<CommentResponseDTO> comments = commentService.commentList(id);
        for (CommentResponseDTO comment : comments) {
            List<ReCommentResponseDTO> replies = reCommentService.getReComments(comment.getId());
            comment.setReplies(replies);
        }

        model.addAttribute("board", boardDTO);

        // 댓글 목록 조회

        // 댓글 조회 (대댓글 포함)

        List<CommentResponseDTO> comments = commentService.commentList(id);


        model.addAttribute("comments", comments);

        return "detail"; // detail.html로 이동
    }

    // 게시글 수정 페이지
    @GetMapping("/board/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        log.info("게시글 수정 페이지로 이동: id={}", id);
        BoardDTO boardDTO = boardService.getBoardById(id);
        model.addAttribute("board", boardDTO);
        return "board/form"; // board/form.html 재활용
    }

    // 게시글 수정 처리
    @PostMapping("/board/{id}/edit")
    public String update(@PathVariable Long id, @ModelAttribute BoardDTO boardDTO) {
        log.info("게시글 수정 처리: id={}, {}", id, boardDTO);
        boardService.saveBoard(boardDTO); // 게시글 수정도 saveBoard로 처리
        return "redirect:/board/" + id; // 수정된 게시글 상세로 리디렉션
    }

    // 게시글 삭제 처리
    @DeleteMapping("/board/{id}/delete")
    public String delete(@PathVariable Long id) {
        log.info("게시글 삭제 처리: id={}", id);
        boardService.deleteBoard(id);
        return "redirect:/board"; // 게시판 목록으로 리디렉션
    }


}
