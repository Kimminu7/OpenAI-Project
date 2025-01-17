package com.example.project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReCommentResponseDTO {
    private Long id; // 대댓글 ID
    private Long parentCommentId; // 원 댓글 ID
    private String content; // 대댓글 내용
    private String authorName; // 대댓글 작성자 이름
    private String authorEmail; // 대댓글 작성자 이메일
    private LocalDateTime createdDate; // 대댓글 작성 날짜
    private boolean isDeleted; // 대댓글 삭제 여부
}
