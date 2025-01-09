package com.example.project.service;

import com.example.project.dto.MemberDTO;
import com.example.project.entity.Member;

public interface MemberService {

    String register(MemberDTO dto);
    MemberDTO login(MemberDTO dto);

    default Member toEntity(MemberDTO dto) {
        Member entity = Member.builder()
                .id(dto.getId())
                .email(dto.getEmail())
                .name(dto.getName())
                .dateOfBirth(dto.getBDate())
                .pw(dto.getPw())
                .build();
        return entity;
    }
    default MemberDTO toDto(Member entity){
        MemberDTO dto = MemberDTO.builder()
                .id(String.valueOf(entity.getId()))
                .email(entity.getEmail())
                .name(entity.getName())
                .BDate(entity.getDateOfBirth())
                .pw(entity.getPw())
                .build();
            return dto;
    }
}
