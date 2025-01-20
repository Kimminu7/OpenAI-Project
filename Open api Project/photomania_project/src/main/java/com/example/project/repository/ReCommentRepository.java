package com.example.project.repository;

import com.example.project.entity.Comment;
import com.example.project.entity.ReComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.List;


@EnableJpaRepositories
public interface ReCommentRepository extends JpaRepository<ReComment, Long> {

    // 원 댓글 ID(parentComment) 객체를 기준으로 삭제되지 않은 대댓글 조회

    // 부모 댓글 ID에 해당하는 대댓글을 삭제되지 않은 상태로 조회
    List<Comment> findByParentCommentIdAndIsDeletedFalse(Long parentCommentId);

    // 대댓글 ID와 삭제되지 않은 상태로 대댓글 조회
    Optional<Comment> findByIdAndIsDeletedFalse(Long id);  // Optional로 변경
}
