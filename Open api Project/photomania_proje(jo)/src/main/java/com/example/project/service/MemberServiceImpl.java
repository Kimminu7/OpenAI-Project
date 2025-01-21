package com.example.project.service;


import com.example.project.dto.MemberDTO;
import com.example.project.entity.Member;
import com.example.project.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public String register(MemberDTO dto) {
        if(memberRepository.findById(dto.getId()).isPresent()){
            return null;
        }

        dto.setPw(passwordEncoder.encode(dto.getPw()));
        Member member = toEntity(dto);
        member =memberRepository.save(member);
        return member.getEmail();
    }

    @Override
    public MemberDTO login(MemberDTO dto) {
        Optional<Member> optionalMember = memberRepository.
                findByEmailAndPw(dto.getEmail(), dto.getPw());
        if(optionalMember.isPresent()){
            return toDto(optionalMember.get());
        }
        return null;
    }
}
