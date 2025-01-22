package com.example.project.service;

import com.example.project.dto.ReCommentRequestDTO;
import com.example.project.dto.ReCommentResponseDTO;

import java.util.List;

public interface ReCommentService {
    // 대댓글 생성
    ReCommentResponseDTO createReComment(String email,Long id ,ReCommentRequestDTO requestDTO);

    // 특정 댓글의 대댓글 조회
    List<ReCommentResponseDTO> getReCommentsByParentCommentId(Long parentCommentId);

    // 대댓글 수정
    ReCommentResponseDTO updateReComment(Long reCommentId, ReCommentRequestDTO requestDTO);

    // 대댓글 삭제
    void deleteReComment(Long reCommentId);
}
