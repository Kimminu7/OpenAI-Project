package com.example.project;

import com.example.project.entity.ExploreEntity;
import com.example.project.repository.ExploreRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@SpringBootTest
public class ExploreTests implements CommandLineRunner {

    @Autowired
    private ExploreRepository exploreRepository;

    @Test
    public void runTest() {
        // 게시물 데이터 생성
        ExploreEntity ex1 = new ExploreEntity();
        ex1.setTitle("First Post");
        ex1.setContent("This is the content of the first post.");
        ex1.setAuthor("Alice");
        ex1.setPhotoUrl("/uploads/photos/first.jpg");

        ExploreEntity ex2 = new ExploreEntity();
        ex2.setTitle("Second Post");
        ex2.setContent("This is the content of the second post.");
        ex2.setAuthor("Bob");
        ex2.setPhotoUrl("/uploads/photos/second.jpg");

        // 데이터 저장
        exploreRepository.save(ex1);
        exploreRepository.save(ex2);

        // 데이터 조회
        exploreRepository.findAll().forEach(post -> {
            System.out.println("Title: " + post.getTitle());
            System.out.println("Author: " + post.getAuthor());
            System.out.println("Photo URL: " + post.getPhotoUrl());
            System.out.println("-------------------------");
        });
    }

    @Override
    public void run(String... args) {
        // CommandLineRunner 로직 (생략 가능)
    }
}