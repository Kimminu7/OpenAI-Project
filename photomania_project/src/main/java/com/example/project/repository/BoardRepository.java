package com.example.project.repository;

import com.example.project.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    // 추가적인 커스텀 쿼리가 필요하면 이곳에 정의할 수 있습니다.

    @Query("SELECT b FROM Board as b order by b.notice desc, b.id desc")
    Page<Board> findByBoardList(Pageable pageable);
}
