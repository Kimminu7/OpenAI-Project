package com.example.project.service;

import com.example.project.dto.ReCommentRequestDTO;
import com.example.project.dto.ReCommentResponseDTO;
import com.example.project.entity.Board;
import com.example.project.entity.Comment;
import com.example.project.entity.Member;
import com.example.project.repository.BoardRepository;
import com.example.project.repository.CommentRepository;
import com.example.project.repository.MemberRepository;
import com.example.project.repository.ReCommentRepository;
import com.example.project.service.ReCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReCommentServiceImpl implements ReCommentService {

    private final CommentRepository commentRepository; // ReCommentRepository 대신 CommentRepository 사용
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    @Override
    public ReCommentResponseDTO createReComment(String email, Long id, ReCommentRequestDTO requestDTO) {
        ReCommentResponseDTO reCommentResponseDTO = null;

        // 대댓글 생성
        Comment comment = Comment.builder()
                .content(requestDTO.getContent())
                .isDeleted(false)
                .build();

        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            comment.setMember(member);

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
                Comment savedReComment = commentRepository.save(comment); // CommentRepository 사용
                reCommentResponseDTO = mapToResponseDTO(savedReComment);
            }
        }

        return reCommentResponseDTO;
    }

    @Override
    public List<ReCommentResponseDTO> getReCommentsByParentCommentId(Long parentCommentId) {
        // 부모 댓글 ID에 해당하는 대댓글 조회
        List<Comment> recomments = commentRepository.findByParentCommentIdAndIsDeletedFalse(parentCommentId);
        return recomments.stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ReCommentResponseDTO updateReComment(Long reCommentId, ReCommentRequestDTO requestDTO) {
        // 대댓글 수정
        Comment comment = commentRepository.findByIdAndIsDeletedFalse(reCommentId)
                .orElseThrow(() -> new IllegalArgumentException("ReComment not found"));

        comment.update(requestDTO.getContent());
        Comment updatedReComment = commentRepository.save(comment); // CommentRepository 사용
        return mapToResponseDTO(updatedReComment);
    }

    @Override
    public void deleteReComment(Long reCommentId) {
        // 대댓글 삭제
        Comment comment = commentRepository.findByIdAndIsDeletedFalse(reCommentId)
                .orElseThrow(() -> new IllegalArgumentException("ReComment not found"));

        comment.setDeleted(true);
        commentRepository.save(comment); // CommentRepository 사용
    }

    // ReComment 객체를 ReCommentResponseDTO로 변환
    private ReCommentResponseDTO mapToResponseDTO(Comment comment) {
        return new ReCommentResponseDTO(
                comment.getId(),
                comment.getParentCommentId() != null ? comment.getParentCommentId().getId() : null, // 부모 ID 확인
                comment.getContent(),
                comment.getMember().getName(),
                comment.getMember().getEmail(),
                comment.getModDate(),  // createdDate로 수정
                comment.isDeleted()
        );
    }
}
