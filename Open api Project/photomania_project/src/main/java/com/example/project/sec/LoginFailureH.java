package com.example.project.sec;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

@Log4j2
public class LoginFailureH implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse res, AuthenticationException exception)
            throws IOException, ServletException {
    String errorMsg;
        if (exception instanceof BadCredentialsException) {
            errorMsg = "아이디 또는 비밀번호가 맞지 않습니다. 다시 확인해주세요.";
        } else if (exception instanceof InternalAuthenticationServiceException) {
            errorMsg = "내부 시스템 문제로 로그인 요청을 처리할 수 없습니다. 관리자에게 문의하세요.";
        } else if (exception instanceof UsernameNotFoundException) {
            errorMsg = "존재하지 않는 계정입니다. 회원가입 후 로그인해주세요.";
        } else if (exception instanceof AuthenticationCredentialsNotFoundException) {
            errorMsg = "인증 요청이 거부되었습니다. 관리자에게 문의하세요.";
        } else {
            errorMsg = "알 수 없는 오류로 로그인 요청을 처리할 수 없습니다. 관리자에게 문의하세요.";
        }
        errorMsg = URLEncoder.encode(errorMsg, "UTF-8"); // 한글 인코딩 깨짐 문제 방지
        res.sendRedirect("/loginError?msg=" + errorMsg); // 에러 페이지 호출
    }
}
