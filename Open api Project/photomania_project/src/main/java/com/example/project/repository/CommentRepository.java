package com.example.project.repository;

import com.example.project.entity.Board;
import com.example.project.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("select c from Comment as c where c.board.id = :id ")
    List<Comment> findByBoardId(@Param("id") Long id);
}