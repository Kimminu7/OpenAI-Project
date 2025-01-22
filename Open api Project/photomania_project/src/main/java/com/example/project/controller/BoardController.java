package com.example.project.controller;

import com.example.project.dto.*;
import com.example.project.entity.Board;
import com.example.project.entity.BoardEntity;
import com.example.project.entity.Member;
import com.example.project.sec.MemberDetails;
import com.example.project.service.BoardService;
import com.example.project.service.CommentService;
import com.example.project.service.ReCommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    // 게시판 목록 페이지
    @GetMapping("/board")
    public String list(@RequestParam(value = "page", defaultValue = "1") int page,
                       @RequestParam(value = "size", defaultValue = "10") int size,
                       Model model) {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(page)
                .size(size)
                .build();

        PageResultDTO<BoardDTO, Board> result = boardService.getList(pageRequestDTO);

        model.addAttribute("boardList", result.getDtoList());
        model.addAttribute("pageResult", result);

        return "board";
    }

    // 게시글 작성 페이지
    @GetMapping("/write")
    public String createForm(Model model) {
       // log.info("게시글 작성 페이지로 이동");
        model.addAttribute("board", new BoardDTO());
        return "write";
    }

    @PostMapping("/board")
    public String create(@RequestParam String title,
                         @RequestParam String content,
                         @RequestParam(required = false) MultipartFile file,
                         Model model, @AuthenticationPrincipal MemberDetails memberDetails) {
        log.info("게시글 작성 처리: 제목={}, 내용={}", title, content);

        try {
            byte[] fileData = null;
            String filename = null;
            if (file != null && !file.isEmpty()) {
                fileData = file.getBytes();
                filename = file.getOriginalFilename();
            }

            BoardDTO boardDTO = BoardDTO.builder()
                    .title(title)
                    .content(content)
                    .filename(filename)
                    .data(fileData)
                    .views(0)
                    .likes(0)
                    .email(memberDetails.getUsername())
                    .build();

            BoardDTO saveDTO = boardService.saveBoard(boardDTO);
            if (saveDTO != null) {
                model.addAttribute("message", "게시글이 성공적으로 작성되었습니다.");
                return "redirect:/board";
            } else {
                model.addAttribute("error", "게시글 저장 중 오류가 발생했습니다.");
                return "write";
            }
        } catch (Exception e) {
            log.error("게시글 작성 중 오류 발생", e);
            model.addAttribute("error", "게시글 저장 중 오류가 발생했습니다.");
            return "write";
        }
    }

    // 게시글 상세 조회
    @GetMapping("/board/{id}")
    public String detail(@PathVariable Long id, Model model, @AuthenticationPrincipal MemberDetails memberDetails) {
        boardService.incrementViews(id);

        BoardDTO boardDTO = boardService.getBoardById(id);
        List<CommentResponseDTO> comments = commentService.commentList(id);


        boolean isAuthor = memberDetails != null && memberDetails.getUsername().equals(boardDTO.getEmail());

        model.addAttribute("board", boardDTO);
        model.addAttribute("comments", comments);
        model.addAttribute("isAuthor", isAuthor);


        return "detail";
    }

    // 게시글 수정 페이지
    @GetMapping("/board/{id}/edit")
    public String editForm(@PathVariable Long id, Model model, @AuthenticationPrincipal MemberDetails memberDetails) {
        BoardDTO boardDTO = boardService.getBoardById(id);

        if (!memberDetails.getUsername().equals(boardDTO.getEmail())) {
            throw new IllegalArgumentException("수정 권한이 없습니다.");
        }

        model.addAttribute("board", boardDTO);
        return "board/form";
    }

    // 게시글 수정 처리
    @PostMapping("/board/{id}/edit")
    public String update(@PathVariable Long id, @ModelAttribute BoardDTO boardDTO, @AuthenticationPrincipal MemberDetails memberDetails) {
        if (!memberDetails.getUsername().equals(boardDTO.getEmail())) {
            throw new IllegalArgumentException("수정 권한이 없습니다.");
        }

        boardService.saveBoard(boardDTO);
        return "redirect:/board/" + id;
    }

    // 게시글 삭제 처리
    @PostMapping("/board/{id}/delete")
    public String delete(@PathVariable Long id, @AuthenticationPrincipal MemberDetails memberDetails) {
        BoardDTO boardDTO = boardService.getBoardById(id);

        if (!memberDetails.getUsername().equals(boardDTO.getEmail())) {
            throw new IllegalArgumentException("삭제 권한이 없습니다.");
        }

        boardService.deleteBoard(id);
        return "redirect:/board";
    }
}
