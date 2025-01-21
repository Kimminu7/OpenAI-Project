package com.example.project.service;

import com.example.project.repository.BoardLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.project.entity.Board;
import com.example.project.entity.BoardLike;

import com.example.project.repository.BoardRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardLikeServiceImpl implements BoardLikeService {

    private final BoardRepository boardRepository;
    private final BoardLikeRepository boardLikeRepository;

    @Override
    public boolean toggleLike(Long boardId, String memberEmail) {
        Optional<BoardLike> existingLike = boardLikeRepository.findByBoardIdAndMemberEmail(boardId, memberEmail);

        if (existingLike.isPresent()) {
            boardLikeRepository.delete(existingLike.get());
            Board board = boardRepository.findById(boardId).orElseThrow();
            board.setLikes(board.getLikes() - 1);  // 좋아요 취소 시 좋아요 수 감소
            boardRepository.save(board);
            return false;  // 좋아요 취소
        } else {
            Board board = boardRepository.findById(boardId).orElseThrow();
            BoardLike newLike = new BoardLike();
            newLike.setBoard(board);
            newLike.setMemberEmail(memberEmail);
            boardLikeRepository.save(newLike);

            board.setLikes(board.getLikes() + 1);  // 좋아요 추가 시 좋아요 수 증가
            boardRepository.save(board);
            return true;  // 좋아요
        }
    }
}

