package com.example.project.repository;

import com.example.project.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
<<<<<<< HEAD
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
=======
>>>>>>> yyb

import java.util.Optional;
import java.util.List;

<<<<<<< HEAD
@EnableJpaRepositories
public interface ReCommentRepository extends JpaRepository<ReComment, Long> {

    // 원 댓글 ID(parentComment) 객체를 기준으로 삭제되지 않은 대댓글 조회
    @Query("select r from ReComment r where r.parentComment = :parentComment")
    List<ReComment> findByParentComment(@Param("parentComment") Comment parentComment);
=======
public interface ReCommentRepository extends JpaRepository<Comment, Long> {
    // 부모 댓글 ID에 해당하는 대댓글을 삭제되지 않은 상태로 조회
    List<Comment> findByParentCommentIdAndIsDeletedFalse(Long parentCommentId);

    // 대댓글 ID와 삭제되지 않은 상태로 대댓글 조회
    Optional<Comment> findByIdAndIsDeletedFalse(Long id);  // Optional로 변경
>>>>>>> yyb
}
