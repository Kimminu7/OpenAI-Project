package com.example.project.controller;

import com.example.project.dto.CommentRequestDTO;
import com.example.project.dto.ReCommentRequestDTO;
import com.example.project.dto.ReCommentResponseDTO;
import com.example.project.service.CommentService;
import com.example.project.service.ReCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final ReCommentService reCommentService;

    // 댓글 작성
    @PostMapping("/board/{id}/comment")
    public String writeComment(@PathVariable Long id, CommentRequestDTO commentRequestDTO, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        commentService.writeComment(commentRequestDTO, id, userDetails.getUsername());
        return "redirect:/board/" + id;
    }

    // 댓글 수정
    @ResponseBody
    @PostMapping("/board/{id}/comment/{commentId}/update")
    public String updateComment(@PathVariable Long id, @PathVariable Long commentId, CommentRequestDTO commentRequestDTO) {
        commentService.updateComment(commentRequestDTO, commentId);
        return "redirect:/board/" + id;
    }

    // 댓글 삭제
    @GetMapping("/board/{id}/comment/{commentId}/remove")
    public String deleteComment(@PathVariable Long id, @PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return "redirect:/board/" + id;
    }

    // 대댓글 작성
    @PostMapping("/board/{id}/comment/{pid}/recomment")
    public String createReComment(@PathVariable Long id, ReCommentRequestDTO requestDTO, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        reCommentService.createReComment(userDetails.getUsername(), id, requestDTO);
        return "redirect:/board/" + id;
    }

    // 부모 댓글 ID로 대댓글 조회
    @GetMapping("/{parentCommentId}")
    public List<ReCommentResponseDTO> getReCommentsByParentCommentId(@PathVariable Long parentCommentId) {
        return reCommentService.getReCommentsByParentCommentId(parentCommentId);
    }

    // 대댓글 수정
    @PutMapping("/{reCommentId}")
    public ReCommentResponseDTO updateReComment(@PathVariable Long reCommentId, @RequestBody ReCommentRequestDTO requestDTO) {
        return reCommentService.updateReComment(reCommentId, requestDTO);
    }

    // 대댓글 삭제
    @DeleteMapping("/{reCommentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReComment(@PathVariable Long reCommentId) {
        reCommentService.deleteReComment(reCommentId);
    }

}
