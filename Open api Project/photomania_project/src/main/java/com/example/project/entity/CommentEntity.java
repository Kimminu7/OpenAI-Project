package com.example.project.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"board", "member"})
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;        // 댓글 내용
    private LocalDateTime regDate; // 작성일
    private LocalDateTime modDate; // 수정일
    private int likes;             // 댓글 좋아요 수

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id") // 게시글 외래 키
    private BoardEntity board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_email") // 회원 email을 외래 키로 사용
    private Member member;

    // 대댓글을 위해 부모 댓글을 연결
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private CommentEntity parent;

    // 댓글 작성 시 등록일 자동 설정
    @PrePersist
    public void prePersist() {
        this.regDate = LocalDateTime.now();
    }

    // 댓글 수정 시 수정일 자동 설정
    @PreUpdate
    public void preUpdate() {
        this.modDate = LocalDateTime.now();
    }
}
