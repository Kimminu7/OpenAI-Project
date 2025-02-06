package com.example.project.dto;

import com.example.project.entity.Member;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MemberDTO {
    private String pw;         // 비밀번호
    private String name;             // 이름
    private String email;            // 이메일
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate BDate;         // 생년월일
    private String gender;           // 성별
    private String nationality;      // 내/외국인 상태
    private String phoneNumber;      // 전화번호
    private String telecomProvider;  // 통신사

    // Member 엔티티를 받아서 MemberDTO로 변환하는 생성자
    public MemberDTO(Member member) {
        this.email = member.getEmail();
        this.name = member.getName();
        this.pw = member.getPw();
        this.BDate = member.getDateOfBirth();
        this.gender = member.getGender();
        this.nationality = member.getNationality();
        this.phoneNumber = member.getPhoneNumber();
        this.telecomProvider = member.getTelecomProvider();
    }

}
