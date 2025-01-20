package com.example.project.service;

import com.example.project.dto.BoardDTO;
import com.example.project.dto.PageRequestDTO;
import com.example.project.dto.PageResultDTO;
import com.example.project.entity.Board;
import com.example.project.entity.Member;
import com.example.project.repository.BoardRepository;
import com.example.project.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    @Override
    public BoardDTO saveBoard(BoardDTO boardDTO) {
        BoardDTO resultDTO = null;
        Optional<Member> optionalMember = memberRepository.findByEmail(boardDTO.getEmail());
        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            Board boardEntity = toEntity(boardDTO);
            boardEntity.setName(member.getName());
            boardEntity.setMember(member);
            // 게시글 저장
            boardEntity = boardRepository.save(boardEntity);
            resultDTO = toDto(boardEntity);
        }

        // 저장된 Entity를 DTO로 변환하여 반환
        return resultDTO;
    }

    @Override
    public List<BoardDTO> getAllBoards() {
        List<Board> boards = boardRepository.findAll();
        return boards.stream()
                .map(this::toDto)  // Entity -> DTO 변환
                .collect(Collectors.toList());
    }

    @Override
    public void deleteBoard(Long id) {
<<<<<<< HEAD
=======
        // 게시글을 찾고 없으면 예외 발생
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Board not found"));

        // 해당 게시글 삭제
>>>>>>> yyb
        boardRepository.deleteById(id);
    }

    @Override
    public void incrementViews(Long id) {
        Board boardEntity = boardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));

        // 조회수 증가
        boardEntity.setViews(boardEntity.getViews() + 1);
        boardRepository.save(boardEntity); // 변경 사항 저장
    }

    @Override
    public BoardDTO getBoardDetail(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid board Id: " + id));
        return new BoardDTO(board);
    }

    @Override
    public PageResultDTO<BoardDTO, Board> getList(PageRequestDTO requestDTO) {
        Pageable pageable = (Pageable) requestDTO.getPageable(Sort.by("id").descending());
        Page<Board> result = boardRepository.findAll(pageable);
        Function<Board, BoardDTO> fn = (entity -> toDto(entity));
        return new PageResultDTO<>(result, fn);
    }

    // 좋아요 증가 처리
    public void incrementLikes(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid board ID"));

        // 좋아요 수 증가
        board.setLikes(board.getLikes() + 1);
        boardRepository.save(board); // 저장
    }

    // 게시글 조회 (조회수 증가 제외)
    @Override
    public BoardDTO getBoardById(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid board ID"));

        return BoardDTO.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .filename(board.getFilename())
                .views(board.getViews())
                .likes(board.getLikes())
                .regDate(board.getRegDate())
                .build();
    }
}
