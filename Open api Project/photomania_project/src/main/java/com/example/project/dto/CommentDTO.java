package com.example.project.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDTO {

    private String email;
    private Long boardId; // 댓글이 속한 게시글의 ID
    private String name; // 댓글 작성자
    private String content; // 댓글 내용
    private LocalDateTime createdDate; // 작성 시간
}
