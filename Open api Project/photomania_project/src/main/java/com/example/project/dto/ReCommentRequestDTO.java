package com.example.project.dto;

import com.example.project.entity.Comment;
import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class ReCommentRequestDTO {
    private Long id;
    private Long parentCommentId; // 원 댓글 ID
    private String content; // 대댓글 내용
    private String authorEmail; // 대댓글 작성자 이메일
    private String authorName;


}
