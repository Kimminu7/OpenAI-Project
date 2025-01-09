package com.example.project.entity;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;


@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Builder
@ToString
public class Member {
    @Id
    private String id;

    @Column(unique = true, nullable = false)
    private String email;            // 이메일
    private String phoneNumber;      // 전화번호

    @Column(nullable = false)
    private String pw;         // 비밀번호
    private String name;             // 이름
    private LocalDate dateOfBirth;   // 생년월일
    private String gender;           // 성별
    private String nationality;      // 내/외국인 상태
    private String telecomProvider;  // 통신사
}
