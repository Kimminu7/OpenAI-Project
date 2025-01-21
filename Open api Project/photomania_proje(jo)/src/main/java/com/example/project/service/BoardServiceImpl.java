package com.example.project.service;

import com.example.project.dto.BoardDTO;
import com.example.project.entity.BoardEntity;
import com.example.project.repository.BoardRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{
    private final BoardRepository boardRepository;


    @Override
    public BoardDTO saveBoard(BoardDTO boardDTO) {
        BoardEntity boardEntity = toEntity(boardDTO);
        boardEntity.setRegDate(LocalDateTime.now());
        boardEntity.setModDate(LocalDateTime.now());

        // 게시글 저장
        BoardEntity savedEntity = boardRepository.save(boardEntity);

        // 저장된 Entity를 DTO로 변환하여 반환
        return toDto(savedEntity);
    }

    @Override
    public List<BoardDTO> getAllBoards() {
        List<BoardEntity> boards = boardRepository.findAll();
        return boards.stream()
                .map(this::toDto)  // Entity -> DTO 변환
                .collect(Collectors.toList());
    }

    @Override
    public BoardDTO getBoardById(Long id) {
        BoardEntity boardEntity = boardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));

        return toDto(boardEntity);  // Entity -> DTO 변환
    }


    @Override
    public void deleteBoard(Long id) {
        boardRepository.deleteById(id);
    }

    @Override
    public void incrementViews(Long id) {
        BoardEntity boardEntity = boardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
        boardEntity.setViews(boardEntity.getViews()+1);
        boardRepository.save(boardEntity);
    }


}
