package com.example.project.service;

import com.example.project.dto.ExploreDTO;
import com.example.project.entity.ExploreEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ExploreService {

    List<ExploreEntity> getAllExplores();
    void deleteExplore(Long id);
    ExploreEntity updateExplore(Long id, ExploreEntity updatedExplore);

    //게시물 저장
    ExploreEntity saveExplore(ExploreEntity exploreEntity);

    // 모든 게시물 조회
    List<ExploreEntity> getAllExplore();

    // ID로 게시물 조회
    ExploreEntity getExploreById(Long id);



}
