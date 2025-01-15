package com.example.project.repository;

import com.example.project.entity.Comment;
import com.example.project.entity.ReComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReCommentRepository extends JpaRepository<ReComment, Long> {

    // 원 댓글 ID(parentComment) 객체를 기준으로 삭제되지 않은 대댓글 조회
    @Query("select r from ReComment r where r.parentComment = :parentComment and r.isDeleted = false")
    List<ReComment> findByParentComment(@Param("parentComment") Comment parentComment);
}
