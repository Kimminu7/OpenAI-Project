package com.example.project.service;

import com.example.project.dto.BoardDTO;
import com.example.project.dto.PageRequestDTO;
import com.example.project.dto.PageResultDTO;
import com.example.project.entity.Board;

import java.util.List;

public interface BoardService {

    BoardDTO saveBoard(BoardDTO boardDTO);

    List<BoardDTO> getAllBoards();


    Board updatePost(BoardDTO updatedPost);

    BoardDTO getBoardById(Long id);

    void deleteBoard(Long id);

    void incrementViews(Long id);

    BoardDTO getBoardDetail(Long id);

    void incrementLikes(Long boardId);


    PageResultDTO<BoardDTO, Board> getList(PageRequestDTO requestDTO);
    // DTO -> Entity 변환
    default Board toEntity(BoardDTO bdto){
        return Board.builder()
                .id(bdto.getId())
                .title(bdto.getTitle())
                .name(bdto.getName())
                .content(bdto.getContent())
                .contentType(bdto.getContentType())
                .views(bdto.getViews())
                .likes(bdto.getLikes())
                .filename(bdto.getFilename())
                .data(bdto.getData())
                .build();

    }
    //Entity -> DTO 변환
    default BoardDTO toDto(Board bentity){
        return BoardDTO.builder()
                .id(bentity.getId())
                .title(bentity.getTitle())
                .name(bentity.getMember().getName())
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
