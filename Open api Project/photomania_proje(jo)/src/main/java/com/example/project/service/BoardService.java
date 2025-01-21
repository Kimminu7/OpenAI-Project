package com.example.project.service;

import com.example.project.dto.BoardDTO;
import com.example.project.entity.BoardEntity;

import java.util.List;

public interface BoardService {

    BoardDTO saveBoard(BoardDTO boardDTO);

    List<BoardDTO> getAllBoards();

    BoardDTO getBoardById(Long id);

    void deleteBoard(Long id);

    void incrementViews(Long id);

    // DTO -> Entity 변환
    default BoardEntity toEntity(BoardDTO bdto){
        return BoardEntity.builder()
                .id(bdto.getId())
                .title(bdto.getTitle())
                .author(bdto.getAuthor())
                .content(bdto.getContent())
                .contentType(bdto.getContentType())
                .views(bdto.getViews())
                .likes(bdto.getLikes())
                .filename(bdto.getFilename())
                .data(bdto.getData())
                .regDate(bdto.getRegDate())
                .modDate(bdto.getModDate())
                .build();

    }
    //Entity -> DTO 변환
    default BoardDTO toDto(BoardEntity bentity){
        return BoardDTO.builder()
                .id(bentity.getId())
                .title(bentity.getTitle())
                .author(bentity.getAuthor())
                .content(bentity.getContent())
                .contentType(bentity.getContentType())
                .views(bentity.getViews())
                .likes(bentity.getLikes())
                .filename(bentity.getFilename())
                .data(bentity.getData())
                .regDate(bentity.getRegDate())
                .modDate(bentity.getModDate())
                .build();

    }
}
