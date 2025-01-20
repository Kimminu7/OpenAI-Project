package com.example.project.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "member")
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String name;

    private String content;
    private String contentType;
    private int views;
    private int likes;
    private String filename;

    private boolean isDeleted;

    @OneToMany(mappedBy = "board",cascade = CascadeType.ALL)
    private List<Comment> comments; // 댓글 필드 추가

    @Lob
    private byte[] data;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_email")
    private Member member;

    public void delete(){
        this.isDeleted =true;
    }

}
