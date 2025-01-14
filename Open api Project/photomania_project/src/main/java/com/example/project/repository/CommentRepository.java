package com.example.project.repository;

import com.example.project.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    // 댓글 조회, 수정, 삭제 등의 메서드는 JpaRepository에서 자동으로 제공됨
}
