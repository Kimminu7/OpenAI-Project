package com.example.project.repository;

import com.example.project.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
    // 추가적인 커스텀 쿼리가 필요하면 이곳에 정의할 수 있습니다.
}
