package com.example.project.service;

import com.example.project.dto.CommentRequestDTO;
import com.example.project.dto.CommentResponseDTO;
import com.example.project.dto.ReCommentResponseDTO;
import com.example.project.entity.Board;
import com.example.project.entity.Comment;
import com.example.project.entity.Member;
import com.example.project.repository.BoardRepository;
import com.example.project.repository.CommentRepository;
import com.example.project.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{

    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final ReCommentService reCommentService;

    @Override
    public Long writeComment(CommentRequestDTO commentRequestDTO, Long boardId, String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("이메일이 존재하지 않습니다."));
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("게시물을 찾을 수 없습니다."));
        Comment result = Comment.builder()
                .content(commentRequestDTO.getContent())
                .board(board)
                .member(member)
                .build();
        commentRepository.save(result);

        return result.getId();
    }

    @Override
    @Transactional
    public List<CommentResponseDTO> commentList(Long id) {
        //Board board = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("게시물을 찾을 수 없습니다."));
        List<Comment> comments = commentRepository.findByBoardId(id);

        return comments.stream()
                .map(comment -> CommentResponseDTO.builder()
                        .id(comment.getId())
                        .content(comment.getContent())
                        .authorName(comment.getMember().getName())
                        .authorEmail(comment.getAuthorEmail())
                        .createdDate(comment.getModDate())
                        .boardId(comment.getBoard().getId())
                        .recomments(toReDtoList(comment.getReplyList()))
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public void updateComment(CommentRequestDTO commentRequestDTO, Long commentId) {
        com.example.project.entity.Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글입니다."));
        comment.update(commentRequestDTO.getContent());
        commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Long commentId) {

        commentRepository.deleteById(commentId);
    }

    @Override
    public List<CommentResponseDTO> getAllComments(Long boardId) {
        // 게시글에 대한 댓글 조회
        List<Comment> comments = commentRepository.findByParentCommentId(boardId);

        // 댓글과 대댓글을 DTO로 변환하여 반환
        return comments.stream()
                .map(comment -> {
                    // 댓글에 대한 대댓글 조회
                    List<ReCommentResponseDTO> reComments = reCommentService.getReCommentsByParentCommentId(comment.getId());

                    // 댓글 정보를 담은 DTO 생성
                    return CommentResponseDTO.builder()
                            .id(comment.getId())
                            .boardId(comment.getBoard().getId())
                            .content(comment.getContent())
                            .authorName(comment.getMember().getName())  // 작성자 이름
                            .authorEmail(comment.getAuthorEmail())     // 작성자 이메일
                            .isDeleted(comment.isDeleted())
                            .recomments(reComments)
                            .build();
                })
                .collect(Collectors.toList());
    }
}
