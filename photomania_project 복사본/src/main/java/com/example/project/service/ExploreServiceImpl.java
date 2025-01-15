package com.example.project.service;

import com.example.project.entity.ExploreEntity;
import com.example.project.repository.ExploreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ExploreServiceImpl implements ExploreService{

    @Autowired
    private ExploreRepository exploreRepository;


    @Override
    public List<ExploreEntity> getAllExplores() {
        return exploreRepository.findAll();
    }

    //삭제
    @Override
    public void deleteExplore(Long id) {
        ExploreEntity explore = getExploreById(id);
        exploreRepository.delete(explore);

    }

    //수정
    @Override
    public ExploreEntity updateExplore(Long id, ExploreEntity updatedExplore) {
        ExploreEntity existingExplore = getExploreById(id);

        //업데이트할 필드 설정
        existingExplore.setTitle(updatedExplore.getTitle());
        existingExplore.setContent(updatedExplore.getContent());
        existingExplore.setAuthor(updatedExplore.getAuthor());
        existingExplore.setPhotoUrl(updatedExplore.getPhotoUrl());

        return exploreRepository.save(existingExplore);
    }

    //저장
    @Override
    public ExploreEntity saveExplore(ExploreEntity exploreEntity) {
        return exploreRepository.save(exploreEntity);
    }

    //조회
    @Override
    public List<ExploreEntity> getAllExplore() {
        return exploreRepository.findAll();
    }

    //아이디 조회
    @Override
    public ExploreEntity getExploreById(Long id) {
        return exploreRepository.findById(id).orElseThrow(()->new RuntimeException("Explore not found with ID:"+id));
    }

}
