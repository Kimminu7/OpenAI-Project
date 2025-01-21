package com.example.project.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
public class BoardEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String author;
    private String content;
    private String contentType;
    private int views;
    private int likes;
    private String filename;

    @Lob
    private byte[] data;

    private LocalDateTime regDate;
    private LocalDateTime modDate;
}
