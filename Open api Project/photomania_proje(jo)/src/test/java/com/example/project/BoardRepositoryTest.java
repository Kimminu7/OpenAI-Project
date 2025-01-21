package com.example.project;


import com.example.project.entity.BoardEntity;
import com.example.project.entity.Member;
import com.example.project.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Optional;
import java.util.stream.IntStream;

@Slf4j
@SpringBootTest
public class BoardRepositoryTest {
    @Autowired
    MemberRepository memberRepository;


    @Test
    public void insertBoard() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            int num = (int) Math.ceil(Math.random() * 10);
            String email = "user" + num + "@gmail.com";

            // 이메일로 회원 조회
            Optional<Member> optionalMember = memberRepository.findByEmailAndPw(email, "password"); // 예시로 'password' 사용

            if (!optionalMember.isPresent()) {
                // Member가 존재하지 않으면 새로 저장
                Member entity = Member.builder()
                        .id("Title" + i)
                        .email(email)
                        .pw("password") // 예시로 'password' 사용
                        .build();
                memberRepository.save(entity);
            }
        });
    }
//    @Test
//    public void updateBoard(){
//        Optional<Board> optionalBoard = boardRepository.findById(1L);
//        if(optionalBoard.isPresent()){
//            Board board = optionalBoard.get();
//            board.setTitle("change Title");
//            board.setContent("change content");
//            boardRepository.save(board);
//        }
//    }
//    @Test
//    public void queryDSLTest(){
//        Pageable pageable = PageRequest.of(0,10, Sort.by("id").descending());
//        QBoard qBoard = QBoard.board;
//        String keyWord = "1";
//        BooleanBuilder builder = new BooleanBuilder();
//        BooleanExpression expression = qBoard.title.contains(keyWord);
//        builder.and(expression);
//        Page<Board> result = boardRepository.findAll(builder,pageable);
//        result.stream().forEach(board -> {
//            log.info("board:{}",board);
//        });
//    }
//    @Test
//    public void queryDSLTest2(){
//        Pageable pageable = PageRequest.of(0,10, Sort.by("id").descending());
//        QBoard qBoard = QBoard.board;
//        String keyWord = "1";
//        BooleanBuilder builder = new BooleanBuilder();
//        BooleanExpression exTitle = qBoard.title.contains(keyWord);
//        BooleanExpression exContent = qBoard.content.contains(keyWord);
//        BooleanExpression exWriter = qBoard.member.name.contains(keyWord);
//        BooleanExpression exAll = exTitle.or(exContent).or(exWriter);
//        builder.and(exAll);
//        builder.and(qBoard.id.gt(0L));
//        Page<Board> result = boardRepository.findAll(builder,pageable);
//        result.stream().forEach(board -> {
//            log.info("board2:{}",board);
//        });
//    }
}
