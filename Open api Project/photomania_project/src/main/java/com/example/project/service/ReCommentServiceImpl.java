package com.example.project.service;

import com.example.project.dto.ReCommentRequestDTO;
import com.example.project.dto.ReCommentResponseDTO;
import com.example.project.entity.Board;
import com.example.project.entity.Comment;
import com.example.project.entity.Member;
import com.example.project.entity.ReComment;
import com.example.project.repository.BoardRepository;
import com.example.project.repository.CommentRepository;
import com.example.project.repository.MemberRepository;
import com.example.project.repository.ReCommentRepository;
import com.example.project.service.ReCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReCommentServiceImpl implements ReCommentService {

    private final ReCommentRepository reCommentRepository;
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    @Override
    public ReCommentResponseDTO createReComment(String email, Long id, ReCommentRequestDTO requestDTO) {
        ReCommentResponseDTO reCommentResponseDTO = null;

        // 대댓글 생성
        ReComment reComment = ReComment.builder()
                .board(parentComment.getBoard())  // 원 댓글의 게시판을 대댓글에 연결
                .member(member)
                .content(requestDTO.getRecontent())
                .parentComment(parentComment)  // 부모 댓글을 참조
                .createdDate(LocalDateTime.now())

        Comment comment = Comment.builder()
                .content(requestDTO.getContent())
                .isDeleted(false)
                .build();

        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            comment.setMember(member);

        return new ReCommentResponseDTO(
                savedReComment.getId(),
                savedReComment.getBoard().getId(),
                savedReComment.getContent(),
                member.getName(),  // 작성자의 이름
                savedReComment.getMember().getEmail(),
                savedReComment.getCreatedDate()
        );
    }

    @Override
    @Transactional
    public List<ReCommentResponseDTO> getReComments(Long parentCommentId) {
        // 원 댓글 조회
        Comment parentComment = commentRepository.findById(parentCommentId)
                .orElseThrow(() -> new IllegalArgumentException("원 댓글이 존재하지 않습니다."));

        // 해당 원 댓글에 달린 대댓글 조회
        List<ReComment> reComments = reCommentRepository.findByParentComment(parentComment);

        return reComments.stream()
                .map(reComment -> new ReCommentResponseDTO(
                        reComment.getId(),
                        reComment.getBoard().getId(),
                        reComment.getContent(),
                        reComment.getMember().getName(),  // 작성자 이름
                        reComment.getMember().getEmail(),
                        reComment.getCreatedDate()
                ))
=======
            Optional<Board> optionalBoard = boardRepository.findById(id);
            if (optionalBoard.isPresent()) {
                Board board = optionalBoard.get();
                comment.setBoard(board);

                Optional<Comment> optionalComment = commentRepository.findById(requestDTO.getParentCommentId());
                if (optionalComment.isPresent()) {
                    Comment parentComment = optionalComment.get();
                    comment.setParentCommentId(parentComment);
                }

                // 대댓글 저장 후 반환
                Comment savedReComment = reCommentRepository.save(comment);
                reCommentResponseDTO = mapToResponseDTO(savedReComment);
            }
        }

        return reCommentResponseDTO;
    }

    @Override
    public List<ReCommentResponseDTO> getReCommentsByParentCommentId(Long parentCommentId) {
        // 부모 댓글 ID에 해당하는 대댓글 조회
        List<Comment> recomments = reCommentRepository.findByParentCommentIdAndIsDeletedFalse(parentCommentId);
        return recomments.stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ReCommentResponseDTO updateReComment(Long reCommentId, ReCommentRequestDTO requestDTO) {
        // 대댓글 수정
        Comment comment = reCommentRepository.findByIdAndIsDeletedFalse(reCommentId)
                .orElseThrow(() -> new IllegalArgumentException("ReComment not found"));

        comment.update(requestDTO.getContent());
        Comment updatedReComment = reCommentRepository.save(comment);
        return mapToResponseDTO(updatedReComment);
    }

    @Override
    public void deleteReComment(Long reCommentId) {
        // 대댓글 삭제
        Comment comment = reCommentRepository.findByIdAndIsDeletedFalse(reCommentId)
                .orElseThrow(() -> new IllegalArgumentException("ReComment not found"));
        comment.setDeleted(true);
        reCommentRepository.save(comment);
    }

    // ReComment 객체를 ReCommentResponseDTO로 변환
    private ReCommentResponseDTO mapToResponseDTO(Comment comment) {
        return new ReCommentResponseDTO(
                comment.getId(),
                comment.getParentCommentId().getId(),
                comment.getContent(),
                comment.getMember().getName(),
                comment.getMember().getEmail(),
                comment.getModDate(),  // createdDate로 수정
                comment.isDeleted()
        );
    }
}
