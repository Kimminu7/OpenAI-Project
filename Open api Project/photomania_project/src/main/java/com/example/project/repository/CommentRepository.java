package com.example.project.repository;

import com.example.project.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    // 게시물 ID를 기준으로 삭제되지 않은 댓글 조회
    @Query("select c from Comment as c where c.board.id = :id and c.parentCommentId is null and c.isDeleted = false")
    List<Comment> findByBoardId(@Param("id") Long id);

    // 원 댓글 ID(parentCommentId)를 기준으로 삭제되지 않은 대댓글 조회
    @Query("select c from Comment as c where c.parentCommentId = :parentCommentId and c.isDeleted = false")
    List<Comment> findByParentCommentId(@Param("parentCommentId") Long parentCommentId);
}
