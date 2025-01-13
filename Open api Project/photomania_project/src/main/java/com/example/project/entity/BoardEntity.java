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
@ToString(exclude = "member")
public class BoardEntity extends BaseEntity {
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
    private LocalDateTime regDate;
<<<<<<< HEAD
    private LocalDateTime mogDate;

    @Lob
    private byte[] data;
=======

    private LocalDateTime modDate;

    private LocalDateTime mogDate;


    @Lob
    private byte[] data;



>>>>>>> yyb
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_email")
    private Member member;

}
