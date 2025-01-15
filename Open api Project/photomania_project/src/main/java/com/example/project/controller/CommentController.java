package com.example.project.controller;

import com.example.project.dto.CommentRequestDTO;
import com.example.project.dto.ReCommentRequestDTO;
import com.example.project.service.CommentService;
import com.example.project.service.ReCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final ReCommentService reCommentService;

    //댓글 작성
    @PostMapping("/board/{id}/comment")
    public String writeComment(@PathVariable Long id, CommentRequestDTO commentRequestDTO, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        commentService.writeComment(commentRequestDTO, id, userDetails.getUsername());

        return "redirect:/board/" + id;
    }

    // 대댓글 작성
    @PostMapping("/board/{id}/comment/{commentId}/reply")
    public String writeReply(@PathVariable Long id, @PathVariable Long commentId, ReCommentRequestDTO reCommentRequestDTO, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        reCommentRequestDTO.setAuthorEmail(userDetails.getUsername()); // 작성자의 이메일 설정

        reCommentService.createReComment(reCommentRequestDTO);
        return "redirect:/board/" + id;
    }

    //댓글 수정
    @ResponseBody
    @PostMapping("/board/{id}/comment/{commentId}/update")
    public String updateComment(@PathVariable Long id, @PathVariable Long commentId, CommentRequestDTO commentRequestDTO) {
        commentService.updateComment(commentRequestDTO, commentId);
        return "redirect:/board/" + id;
    }

    //댓글 삭제
    @GetMapping("/board/{id}/comment/{commentId}/remove")
    public String deleteComment(@PathVariable Long id, @PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return "redirect:/board/" + id;
    }
}
