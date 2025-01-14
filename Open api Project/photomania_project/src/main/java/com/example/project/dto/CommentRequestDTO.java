package com.example.project.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequestDTO {

    private Long boardId; // 게시물 ID
    private String content; // 댓글 내용
    private String authorEmail; // 댓글 작성자 이메일
}
