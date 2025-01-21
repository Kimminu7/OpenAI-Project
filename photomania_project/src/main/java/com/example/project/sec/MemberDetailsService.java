package com.example.project.sec;

import com.example.project.entity.Member;
import com.example.project.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberDetailsService implements UserDetailsService {

    @Autowired
    MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> optionalMember = memberRepository.findById(username);
        if(!optionalMember.isPresent()){
            throw new UsernameNotFoundException(username);
        }
        return new MemberDetails(optionalMember.get());
    }
}
