package com.example.project.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReComment extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Board와 ManyToOne 관계
    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    // Member와 ManyToOne 관계 (email을 기준으로 연결)
    @ManyToOne
    @JoinColumn(name = "member_id")  // email 대신 ID로 연결
    private Member member;

    @Column(nullable = false)
    private String content;

    // 댓글을 참조하는 ManyToOne 관계로 수정
    @ManyToOne
    @JoinColumn(name = "parent_comment_id")
    private Comment parentComment; // 부모 댓글을 참조하는 관계로 수정

    @Column(nullable = false)
    private LocalDateTime createdDate;

}
