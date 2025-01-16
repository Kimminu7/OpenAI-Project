package com.example.project.repository;

import com.example.project.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {
    @Query("select m from Member m where m.email = :email and m.pw = :pw")
    Optional<Member> findByEmailAndPw(@Param("email") String email, @Param("pw") String pw);
}