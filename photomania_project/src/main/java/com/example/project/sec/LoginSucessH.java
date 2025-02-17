package com.example.project.sec;


import com.example.project.dto.MemberDTO;
import com.example.project.entity.Role;
import com.example.project.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class LoginSucessH implements AuthenticationSuccessHandler {
    @Autowired
    MemberService memberService;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        log.info("onAuthenticationSuccess");
        MemberDetails memberDetails =(MemberDetails) authentication.getPrincipal();
        if (memberDetails == null) {
            response.sendRedirect("/login");
        } else {
            // 세션에 사용자 정보 저장
            MemberDTO memberDTO = memberService.toDto(memberDetails.getMember());
            HttpSession httpSession = request.getSession();
            httpSession.setAttribute("member", memberDTO);

            List<String> roleNames = new ArrayList<>();
            memberDetails.getAuthorities().forEach(authority -> {
                roleNames.add(authority.getAuthority());
            });

            if (roleNames.contains(Role.ROLE_ADMIN.value())) {
                response.sendRedirect("/main"); // 관리자 페이지
            } else if (roleNames.contains(Role.ROLE_USER.value())) {
                response.sendRedirect("/main"); // 사용자 페이지
            } else {
                response.sendRedirect("/main");
            }
        }

    }
}
