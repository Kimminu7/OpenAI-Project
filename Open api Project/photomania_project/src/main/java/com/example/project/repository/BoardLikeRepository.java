package com.example.project.repository;

import com.example.project.entity.BoardLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardLikeRepository extends JpaRepository<BoardLike, Long> {

    // 특정 게시글과 사용자의 좋아요를 찾는 쿼리 메서드
    Optional<BoardLike> findByBoardIdAndMemberEmail(Long boardId, String memberEmail);
}
