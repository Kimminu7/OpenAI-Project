package com.example.project.service;

public interface BoardLikeService {
    boolean toggleLike(Long boardId, String memberEmail);
}
