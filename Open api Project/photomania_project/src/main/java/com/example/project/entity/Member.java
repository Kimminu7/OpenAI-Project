package com.example.project.entity;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Builder
@ToString(exclude ="boardList")
public class Member extends BaseEntity{
    @Id
    private String email;
    private String phoneNumber;      // 전화번호

    @Column(nullable = false)
    private String pw;         // 비밀번호
    private String name;             // 이름
    private LocalDate dateOfBirth;   // 생년월일
    private String gender;           // 성별
    private String nationality;      // 내/외국인 상태
    private String telecomProvider;  // 통신사
    @Builder.Default
    @OneToMany(mappedBy = "member",cascade=CascadeType.ALL)
    private List<BoardEntity> boardList = new ArrayList<>();
    public void addBoard(BoardEntity board){
        boardList.add(board);
        board.setMember(this);
    }
}
