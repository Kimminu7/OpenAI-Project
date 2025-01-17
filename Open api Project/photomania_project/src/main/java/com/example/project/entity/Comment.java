package com.example.project.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "comment")
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "BOARD_ID")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "MEMBER_email")
    private Member member;

    @Column(name = "parent_comment_id")
    private Long parentCommentId; // 원 댓글 ID (대댓글 구분용)

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false; // 삭제 여부, 기본값은 false

    // 댓글 내용 업데이트 메서드
    public void update(String content) {
        this.content = content;
    }

    // 작성자의 이메일 반환 메서드

    public String getAuthorEmail() {
        return this.member != null ? this.member.getEmail() : null;
    }

}
