package com.example.project.controller;

import com.example.project.dto.BoardDTO;
import com.example.project.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.persistence.PostRemove;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    // 게시판 목록 페이지
    @GetMapping("/board")
    public String list(Model model) {
        log.info("게시판 목록 페이지 조회");
        List<BoardDTO> boardList = boardService.getAllBoards();  // getBoardList -> getAllBoards로 수정
        model.addAttribute("boardList", boardList);
        return "board"; // board/list.html로 연결
    }

    // 게시글 작성 페이지
    @GetMapping("/write")
    public String createForm(Model model) {
        log.info("게시글 작성 페이지로 이동");
        model.addAttribute("board", new BoardDTO());
        return "/write"; // board/write.html로 연결
    }

    // 게시글 작성 처리
    @PostMapping("/board")
    public String create(@ModelAttribute BoardDTO boardDTO) {
        log.info("게시글 작성 처리: {}", boardDTO);
        boardService.saveBoard(boardDTO);  // createBoard -> saveBoard로 수정
        return "redirect:/board"; // 게시판 목록으로 리디렉션
    }

    // 게시글 상세 조회
    @GetMapping("/board/{id}")
    public String detail(@PathVariable Long id, Model model) {
        log.info("게시글 상세 조회: id={}", id);
        boardService.incrementViews(id); // 조회수 증가 메서드 호출
        BoardDTO boardDTO = boardService.getBoardById(id);  // 최신 데이터 조회
        model.addAttribute("board", boardDTO);
        return "board/detail"; // board/detail.html로 연결
    }

    // 게시글 수정 페이지
    @GetMapping("/board/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        log.info("게시글 수정 페이지로 이동: id={}", id);
        BoardDTO boardDTO = boardService.getBoardById(id);
        model.addAttribute("board", boardDTO);
        return "board/form"; // board/form.html 재활용
    }

    // 게시글 수정 처리
    @PostMapping("/board/{id}/edit")
    public String update(@PathVariable Long id, @ModelAttribute BoardDTO boardDTO) {
        log.info("게시글 수정 처리: id={}, {}", id, boardDTO);
        boardService.saveBoard(boardDTO); // 게시글 수정도 saveBoard로 처리 (기존 saveBoard와 동일)
        return "redirect:/board/" + id; // 수정된 게시글 상세로 리디렉션
    }

    // 게시글 삭제 처리
    @PostMapping("/board/{id}/delete")
    public String delete(@PathVariable Long id) {
        log.info("게시글 삭제 처리: id={}", id);
        boardService.deleteBoard(id);
        return "redirect:/board"; // 게시판 목록으로 리디렉션
    }

    @PostMapping("/write")
    public String writeaccess(){
        log.info("작성페이지 진입");
        return "/write";
    }
}
