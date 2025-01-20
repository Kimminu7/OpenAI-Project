package com.example.project.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReCommentResponseDTO {
    private Long id; // 대댓글 ID
<<<<<<< HEAD
    private Long boardId; // 게시물 ID
    private String Recontent; // 대댓글 내용
=======
    private Long parentCommentId; // 원 댓글 ID
    private String content; // 대댓글 내용
>>>>>>> yyb
    private String authorName; // 대댓글 작성자 이름
    private String authorEmail; // 대댓글 작성자 이메일
    private LocalDateTime createdDate; // 대댓글 작성 날짜
    private boolean isDeleted; // 대댓글 삭제 여부
<<<<<<< HEAD

    public ReCommentResponseDTO(Long id, Long id1, String content, String name, String email, LocalDateTime createdDate) {
    }
=======
>>>>>>> yyb
}
