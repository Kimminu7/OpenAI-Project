package com.example.project.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "exploreBuilder")
@ToString(exclude = "member")
public class ExploreEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 고유 id

    private String  email;

    private String title;

    @Column(length = 1500)
    private String content;

    private String author;

    private LocalDateTime createdAt; //날짜와 시간 저장

    @Builder.Default
    private int hit = 0;
    private int likes; //좋아요
    private int goods; //굿

    @Column(length =1000) //사진 파일 경로 또는 URL
    private String photoUrl;

    @ManyToOne(fetch =FetchType.EAGER)
    @JoinColumn(name = "member_email")
    private Member member;


}
