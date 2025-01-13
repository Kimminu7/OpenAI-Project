package com.example.project.service;

import com.example.project.dto.BoardDTO;
import com.example.project.dto.PageRequestDTO;
import com.example.project.dto.PageResultDTO;
import com.example.project.entity.BoardEntity;

import java.util.List;

public interface BoardService {



    BoardDTO saveBoard(BoardDTO boardDTO);

    List<BoardDTO> getAllBoards();

    BoardDTO getBoardById(Long id);

    void deleteBoard(Long id);

    void incrementViews(Long id);

    BoardDTO getBoardDetail(Long id);

    PageResultDTO<BoardDTO,BoardEntity> getList(PageRequestDTO requestDTO);
    // DTO -> Entity 변환
    default BoardEntity toEntity(BoardDTO bdto){
        return BoardEntity.builder()
                .id(bdto.getId())
                .title(bdto.getTitle())
                .name(bdto.getName())
                .content(bdto.getContent())
                .contentType(bdto.getContentType())
                .views(bdto.getViews())
                .likes(bdto.getLikes())
                .filename(bdto.getFilename())
                .regDate(bdto.getRegDate())
                .modDate(bdto.getMogDate())
                .data(bdto.getData())
                .build();

    }
    //Entity -> DTO 변환
    default BoardDTO toDto(BoardEntity bentity){
        return BoardDTO.builder()
                .id(bentity.getId())
                .title(bentity.getTitle())
                .name(bentity.getName())
                .content(bentity.getContent())
                .contentType(bentity.getContentType())
                .views(bentity.getViews())
                .likes(bentity.getLikes())
                .filename(bentity.getFilename())
                .regDate(bentity.getRegDate())
                .mogDate(bentity.getModDate())
                .data(bentity.getData())
                .build();

    }
}
