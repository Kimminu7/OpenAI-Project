package com.example.project.service;

import com.example.project.dto.BoardDTO;
import com.example.project.dto.CommentRequestDTO;
import com.example.project.dto.CommentResponseDTO;
import com.example.project.dto.ReCommentResponseDTO;
import com.example.project.entity.Board;
import com.example.project.entity.Comment;

import java.util.List;
import java.util.stream.Collectors;

public interface CommentService {


    //댓글 작성
    Long writeComment(CommentRequestDTO commentRequestDTO, Long boardId, String email);

    //댓글 조회
    List<CommentResponseDTO> commentList(Long id);

    //댓글 수정
    void updateComment(CommentRequestDTO commentRequestDTO, Long commentId);

     //댓글 삭제

    void deleteComment(Long commentId);


    List<CommentResponseDTO> getAllComments(Long boardId);

    //Entity -> DTO 변환
    default ReCommentResponseDTO toReDto(Comment entity){
        return ReCommentResponseDTO.builder()
                .id(entity.getId())
                .content(entity.getContent())
                .authorName(entity.getMember().getName())
                .authorEmail(entity.getAuthorEmail())
                .createdDate(entity.getModDate())
                .parentCommentId(entity.getParentCommentId().getId())
                .isDeleted(entity.isDeleted())
                .build();

    }

    default List<ReCommentResponseDTO> toReDtoList(List<Comment> entityList) {
        return entityList.stream().map(c -> toReDto(c)).collect(Collectors.toList());
    }
}