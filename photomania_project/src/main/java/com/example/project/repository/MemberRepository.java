package com.example.project.repository;

import com.example.project.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {

    // 이메일로 회원 찾기
    Optional<Member> findByEmail(String email);

    @Query("select m from Member m where m.email = :email and m.pw = :pw")
    Optional<Member> findByEmailAndPw(@Param("email") String email, @Param("pw") String pw);
}
