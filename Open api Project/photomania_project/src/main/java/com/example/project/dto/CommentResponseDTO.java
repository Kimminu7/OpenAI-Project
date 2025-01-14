package com.example.project.dto;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CommentResponseDTO {

    private Long id; // 댓글 ID
    private Long boardId; // 게시물 ID
    private String content; // 댓글 내용
    private String authorName;
    private String authorEmail; // 댓글 작성자 이메일
    private LocalDateTime createdDate; // 작성 날짜
    private boolean isDeleted; // 삭제 여부
}
