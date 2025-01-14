package com.example.project.service;

import com.example.project.dto.CommentRequestDTO;
import com.example.project.dto.CommentResponseDTO;

import java.util.List;

public interface CommentService {


    //댓글 작성
    Long writeComment(CommentRequestDTO commentRequestDTO, Long boardId, String email);

    //댓글 조회
    List<CommentResponseDTO> commentList(Long id);

    //댓글 수정
    void updateComment(CommentRequestDTO commentRequestDTO, Long commentId);

     //댓글 삭제

    void deleteComment(Long commentId);
}