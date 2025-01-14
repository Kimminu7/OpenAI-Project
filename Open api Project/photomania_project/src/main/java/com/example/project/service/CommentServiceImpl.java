package com.example.project.service;

import com.example.project.entity.BoardEntity;
import com.example.project.entity.CommentEntity;
import com.example.project.entity.Member;
import com.example.project.repository.BoardRepository;
import com.example.project.repository.CommentRepository;
import com.example.project.repository.MemberRepository;
import com.example.project.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BoardRepository boardRepository;

    // 댓글 작성
    @Override
    public CommentEntity addComment(Long boardId, String email, String content) {
        Optional<Member> memberOpt = memberRepository.findById(email);
        if (!memberOpt.isPresent()) {
            throw new IllegalArgumentException("존재하지 않는 회원입니다.");
        }

        Optional<BoardEntity> boardOpt = boardRepository.findById(boardId);
        if (!boardOpt.isPresent()) {
            throw new IllegalArgumentException("존재하지 않는 게시글입니다.");
        }

        Member member = memberOpt.get();
        BoardEntity board = boardOpt.get();

        CommentEntity comment = CommentEntity.builder()
                .content(content)
                .board(board)
                .member(member)
                .likes(0) // 기본 좋아요 수는 0
                .build();

        return commentRepository.save(comment);
    }

    // 댓글 좋아요 처리
    @Override
    public void likeComment(Long commentId) {
        CommentEntity comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글입니다."));

        comment.setLikes(comment.getLikes() + 1); // 좋아요 수 증가
        commentRepository.save(comment);
    }

    // 댓글 수정
    @Override
    public CommentEntity updateComment(Long commentId, String newContent) {
        CommentEntity comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글입니다."));

        comment.setContent(newContent);
        return commentRepository.save(comment);
    }

    // 댓글 삭제
    @Override
    public void deleteComment(Long commentId) {
        CommentEntity comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글입니다."));

        commentRepository.delete(comment);
    }
}
