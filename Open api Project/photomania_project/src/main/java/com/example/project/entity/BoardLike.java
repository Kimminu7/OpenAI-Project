package com.example.project.entity;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;



@Entity
@Table(name = "board_like")
@Getter
@Setter
public class BoardLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @Column(name = "member_email", nullable = false)
    private String memberEmail;
}
