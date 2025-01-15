package com.example.project.service;

import com.example.project.dto.ReCommentRequestDTO;
import com.example.project.dto.ReCommentResponseDTO;

import java.util.List;

public interface ReCommentService {
    // 대댓글 작성
    ReCommentResponseDTO createReComment(ReCommentRequestDTO requestDTO);

    // 대댓글 조회
    List<ReCommentResponseDTO> getReComments(Long parentCommentId);
}
