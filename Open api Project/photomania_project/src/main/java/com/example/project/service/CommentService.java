package com.example.project.service;

import com.example.project.entity.CommentEntity;

public interface CommentService {

    // 댓글 작성
    CommentEntity addComment(Long boardId, String email, String content);

    // 댓글 좋아요 처리
    void likeComment(Long commentId);

    // 댓글 수정
    CommentEntity updateComment(Long commentId, String newContent);

    // 댓글 삭제
    void deleteComment(Long commentId);
}
