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
        return http
                .csrf().disable() // 개발 단계에서만 비활성화
                .authorizeRequests()
                .antMatchers("/register", "/login", "/main", "/board", "/detail/{id}").permitAll()
                .antMatchers("/admin/**").authenticated()
                .antMatchers("/css/**", "/images/**", "/js/**").permitAll()
                .antMatchers("/main").authenticated()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/main")
                .usernameParameter("id")
                .passwordParameter("pw")
                .loginProcessingUrl("/dologin")
                .successHandler(successHandler())
                .failureUrl("/login?error=true")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout") // 로그아웃 URL 설정
                .logoutSuccessUrl("/main") // 로그아웃 성공 후 이동할 페이지
                .invalidateHttpSession(true) // 세션 무효화
                .deleteCookies("JSESSIONID") // 쿠키 삭제
                .permitAll()
                .and()

                .build();
    }
}
