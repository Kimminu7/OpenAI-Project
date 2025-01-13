package com.example.project.dto;

<<<<<<< HEAD
import com.example.project.entity.BaseEntity;
=======


import com.example.project.entity.BaseEntity;

>>>>>>> yyb
import com.example.project.entity.BoardEntity;
import lombok.*;
import java.time.LocalDateTime;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardDTO  {

    private Long id;  // 게시글 ID
    private String title;  // 게시글 제목
    private String name;  // 게시글 작성자
    private String content;  // 게시글 내용
    private String contentType;  // 게시글 유형
    private int views;  // 조회수
    private int likes;  // 추천수
    private String filename;  // 파일 이름
    private byte[] data;
<<<<<<< HEAD
    private LocalDateTime regDate;
    private LocalDateTime mogDate;
=======



    private LocalDateTime regDate;
    private LocalDateTime mogDate;

>>>>>>> yyb
    public BoardDTO(BoardEntity board) {
    }

}
