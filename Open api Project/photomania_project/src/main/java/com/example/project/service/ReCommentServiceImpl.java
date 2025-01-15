package com.example.project.service;

import com.example.project.dto.ReCommentRequestDTO;
import com.example.project.dto.ReCommentResponseDTO;
import com.example.project.entity.Comment;
import com.example.project.entity.Member;
import com.example.project.entity.ReComment;
import com.example.project.repository.CommentRepository;
import com.example.project.repository.MemberRepository;
import com.example.project.repository.ReCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ReCommentServiceImpl implements ReCommentService {

    private final ReCommentRepository reCommentRepository;
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;  // MemberRepository 추가

    @Override
    @Transactional
    public ReCommentResponseDTO createReComment(ReCommentRequestDTO requestDTO) {
        // 원 댓글 조회
        Comment parentComment = commentRepository.findById(requestDTO.getParentCommentId())
                .orElseThrow(() -> new IllegalArgumentException("원 댓글이 존재하지 않습니다."));

        // 대댓글 작성자 조회 (이메일을 통해 Member 조회)
        Member member = memberRepository.findByEmail(requestDTO.getAuthorEmail())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        // 대댓글 생성
        ReComment reComment = ReComment.builder()
                .board(parentComment.getBoard())  // 원 댓글의 게시판을 대댓글에 연결
                .member(member)
                .content(requestDTO.getContent())
                .parentComment(parentComment)  // 부모 댓글을 참조
                .createdDate(LocalDateTime.now())
                .build();

        // 대댓글 저장
        ReComment savedReComment = reCommentRepository.save(reComment);

        return new ReCommentResponseDTO(
                savedReComment.getId(),
                savedReComment.getBoard().getId(),
                savedReComment.getContent(),
                member.getName(),  // 작성자의 이름
                savedReComment.getMember().getEmail(),
                savedReComment.getCreatedDate(),
                savedReComment.isDeleted()
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
                        reComment.getCreatedDate(),
                        reComment.isDeleted()
                ))
                .collect(Collectors.toList());
    }
}
