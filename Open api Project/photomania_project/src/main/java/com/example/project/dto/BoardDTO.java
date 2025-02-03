package com.example.project.dto;

import com.example.project.entity.BaseEntity;
import lombok.*;
import com.example.project.entity.Board;
import com.example.project.entity.Member;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import com.example.project.entity.Comment;
import java.time.LocalDateTime;
import java.util.List;

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
    private String email;
    private LocalDateTime regDate;
    private LocalDateTime mogDate;
    private MemberDTO member; // 🔹 여기서 MemberDTO를 사용하고 있는지 확인 필요

    public BoardDTO(Board board) {
    }

}
