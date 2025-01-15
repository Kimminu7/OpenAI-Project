package com.example.project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReCommentResponseDTO {
    private Long id; // 대댓글 ID
    private Long boardId; // 게시물 ID
    private String content; // 대댓글 내용
    private String authorName; // 대댓글 작성자 이름
    private String authorEmail; // 대댓글 작성자 이메일
    private LocalDateTime createdDate; // 대댓글 작성 날짜
    private boolean isDeleted; // 대댓글 삭제 여부
    private List<ReCommentResponseDTO> replies;

    public ReCommentResponseDTO(Long id, Long id1, String content, String name, String email, LocalDateTime createdDate) {
    }
}
