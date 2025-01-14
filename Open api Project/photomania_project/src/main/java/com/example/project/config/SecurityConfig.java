package com.example.project.config;

import com.example.project.sec.LoginFailureH;
import com.example.project.sec.LoginSucessH;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {



    @Bean
    public LoginSucessH successHandler() {
        return new LoginSucessH();
    }
    @Bean
    public LoginFailureH failureHandler() {
        return new LoginFailureH();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable()
                .authorizeRequests()
                // 회원가입과 로그인 페이지는 인증 없이 접근 가능
                .antMatchers("/register", "/login","/main","/board","/detail/{id}").permitAll()
                // 관리자 경로는 인증이 필요
                .antMatchers("/admin/**").authenticated()
                // CSS, 이미지, JS 파일은 인증 없이 접근 가능
                .antMatchers("/css/**", "/images/**", "/js/**").permitAll()
                // main2 페이지는 인증된 사용자만 접근 가능
                .antMatchers("/main").authenticated()
                // 그 외 모든 요청은 인증 필요
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/main")
                .usernameParameter("id")
                .passwordParameter("pw")
                .loginProcessingUrl("/dologin")
                .successHandler(successHandler())
                //.defaultSuccessUrl("/main2", true) // 로그인 성공 후 main2로 리디렉션
                .failureUrl("/login?error=true") // 로그인 실패 후 리디렉션
                .permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/main") // 로그아웃 후 main 페이지로 리디렉션
                .permitAll()
                .and()
                .build();
    }

}