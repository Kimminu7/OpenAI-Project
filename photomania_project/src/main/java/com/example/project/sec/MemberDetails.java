package com.example.project.sec;

import com.example.project.entity.Member;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
public class MemberDetails implements UserDetails {


    private final Member member;

    public MemberDetails(Member member){
        this.member = member;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collectors = new ArrayList<>();
        collectors.add(new SimpleGrantedAuthority(member.getRole().value()));
        return collectors;
    }

    @Override
    public String getPassword() {

        return member.getPw();
    }

    @Override
    public String getUsername() {
        Collection<GrantedAuthority> collectors = new ArrayList<>();
        collectors.add(new SimpleGrantedAuthority(member.getRole().value()));
        return member.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {

        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {

        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
