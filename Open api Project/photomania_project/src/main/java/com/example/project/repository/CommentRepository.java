package com.example.project.repository;

import com.example.project.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    // 게시물 ID를 기준으로 삭제되지 않은 댓글 조회
    @Query("select c from Comment as c where c.board.id = :id and c.parentCommentId is null and c.isDeleted = false")
    List<Comment> findByBoardId(@Param("id") Long id);

    // 원 댓글 ID(parentCommentId)를 기준으로 삭제되지 않은 대댓글 조회
    @Query("select c from Comment as c where c.parentCommentId.id = :parentCommentId and c.isDeleted = false")
    List<Comment> findByParentCommentIdAndIsDeletedFalse(@Param("parentCommentId") Long parentCommentId);

    // 부모 댓글 ID에 해당하는 댓글 조회
    @Query("select c from Comment c where c.parentCommentId.id = :parentCommentId and c.isDeleted = false")
    List<Comment> findByParentCommentId(@Param("parentCommentId") Long parentCommentId);

    // 삭제되지 않은 댓글을 ID 기준으로 조회
    @Query("select c from Comment c where c.id = :id and c.isDeleted = false")
    Optional<Comment> findByIdAndIsDeletedFalse(@Param("id") Long id);
    
}
