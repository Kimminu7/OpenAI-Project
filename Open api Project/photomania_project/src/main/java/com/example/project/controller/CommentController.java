package com.example.project.controller;

import com.example.project.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    // 댓글 작성
    @PostMapping("/{boardId}")
    public String addComment(@PathVariable Long boardId,
                             @RequestParam String email,
                             @RequestParam String content,
                             Model model) {
        commentService.addComment(boardId, email, content);
        return "redirect:/board/" + boardId;  // 게시글 상세 페이지로 리디렉션
    }

    // 댓글 좋아요 처리
    @PostMapping("/{commentId}/like")
    public String likeComment(@PathVariable Long commentId, Model model) {
        commentService.likeComment(commentId);
        return "redirect:/board";  // 게시판 목록 페이지로 리디렉션
    }

    // 댓글 수정 처리
    @PostMapping("/{commentId}/update")
    public String updateComment(@PathVariable Long commentId,
                                @RequestParam String newContent,
                                Model model) {
        commentService.updateComment(commentId, newContent);
        return "redirect:/board";  // 수정 후 게시글 상세 페이지로 리디렉션
    }

    // 댓글 삭제 처리
    @PostMapping("/{commentId}/delete")
    public String deleteComment(@PathVariable Long commentId, Model model) {
        commentService.deleteComment(commentId);
        return "redirect:/board";  // 삭제 후 게시글 상세 페이지로 리디렉션
    }
}
