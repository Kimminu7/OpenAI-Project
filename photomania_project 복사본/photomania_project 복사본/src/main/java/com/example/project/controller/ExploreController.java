package com.example.project.controller;

import com.example.project.entity.ExploreEntity;
import com.example.project.service.ExploreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ExploreController {

    @Autowired private ExploreService exploreService;


    // 탐색 화면 보여주는 메서드
    @GetMapping("/explore")
    public String showexploreForm() {
        log.info("탐색 페이지 이동");
        return "explore";
    }

    // 업로드 화면 보여주는 메소드
    @GetMapping("/upload")
    public String showUploadForm() {
        log.info("업로드 페이지 이동");
        return "upload";
    }
    // 게시물 저장
    @PostMapping
    public ResponseEntity<ExploreEntity> saveExplore(@RequestBody ExploreEntity exploreEntity) {
        ExploreEntity savedExplore = exploreService.saveExplore(exploreEntity);
        return ResponseEntity.ok(savedExplore);
    }

    // 모든 게시물 조회
    @GetMapping
    public ResponseEntity<List<ExploreEntity>> getAllExplores() {
        List<ExploreEntity> explores = exploreService.getAllExplores();
        return ResponseEntity.ok(explores);
    }

    // ID로 게시물 조회
    @GetMapping("/{id}")
    public ResponseEntity<ExploreEntity> getExploreById(@PathVariable Long id) {
        ExploreEntity explore = exploreService.getExploreById(id);
        return ResponseEntity.ok(explore);
    }

    // 게시물 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteExplore(@PathVariable Long id) {
        exploreService.deleteExplore(id);
        return ResponseEntity.ok("Explore deleted with ID: " + id);
    }

    // 게시물 수정
    @PutMapping("/{id}")
    public ResponseEntity<ExploreEntity> updateExplore(
            @PathVariable Long id, @RequestBody ExploreEntity updatedExplore) {
        ExploreEntity explore = exploreService.updateExplore(id, updatedExplore);
        return ResponseEntity.ok(explore);
    }

}
