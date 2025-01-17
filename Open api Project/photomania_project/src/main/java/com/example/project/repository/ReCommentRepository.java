package com.example.project.repository;

import com.example.project.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface ReCommentRepository extends JpaRepository<Comment, Long> {
    // 부모 댓글 ID에 해당하는 대댓글을 삭제되지 않은 상태로 조회
    List<Comment> findByParentCommentIdAndIsDeletedFalse(Long parentCommentId);

    // 대댓글 ID와 삭제되지 않은 상태로 대댓글 조회
    Optional<Comment> findByIdAndIsDeletedFalse(Long id);  // Optional로 변경
}
