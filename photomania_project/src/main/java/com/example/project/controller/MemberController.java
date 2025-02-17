package com.example.project.controller;

import com.example.project.dto.MemberDTO;
import com.example.project.entity.Member;
import com.example.project.entity.Role;
import com.example.project.repository.MemberRepository;
import com.example.project.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원가입 폼을 보여주는 메서드
    @GetMapping("/register")
    public String showRegistrationForm() {
        log.info("회원가입 페이지로 이동");
        return "register";
    }


    // 회원가입 처리 메서드
    @PostMapping("/register")
    public String registerMember(@ModelAttribute MemberDTO memberDto) {
        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(memberDto.getPw());

        // MemberDTO를 Member 엔티티로 변환
        Member member = new Member();
        member.setPw(encodedPassword);
        member.setName(memberDto.getName());
        member.setEmail(memberDto.getEmail());
        member.setDateOfBirth(memberDto.getBDate());
        member.setGender(memberDto.getGender());
        member.setNationality(memberDto.getNationality());
        member.setPhoneNumber(memberDto.getPhoneNumber());
        member.setTelecomProvider(memberDto.getTelecomProvider());
        member.setRole(Role.ROLE_USER);
        // 회원 저장
        memberRepository.save(member);

        // 회원가입 후 로그인 페이지로 리디렉션
        return "redirect:/login";
    }


    @GetMapping("/login")
    public String showLoginFrom(){
        log.info("로그인 화면 접속");
        return "/login";
    }
//     로그인 폼을 보여주는 메서드
//    @PostMapping("/login")
//    public String login(@RequestParam("id") String username,
//                        @RequestParam("pw") String password,
//                        RedirectAttributes redirectAttributes) {
//
//        // 간단한 로그인 유효성 검사 (예시)
//        if ("admin".equals(username) && "admin123".equals(password)) {
//            redirectAttributes.addFlashAttribute("access", "로그인완");
//            // 로그인 성공 시 메인 페이지로 리디렉션
//            return "redirect:/main";
//        } else {
//            // 로그인 실패 시 에러 메시지 추가 후 로그인 페이지로 리디렉션
//            redirectAttributes.addFlashAttribute("error", "아이디 또는 비밀번호가 잘못되었습니다.");
//            return "redirect:/login";
//        }
//    }

    @GetMapping("/main")
    public String mainPage() {

        return "main";  // main.html 페이지를 반환
    }
    @PostMapping("/main")
    public String showMain(){

        return "/main";
    }

    @GetMapping("/find")
    public String find(){
        return "find";
    }
    @GetMapping("/member/board")
    public String board(){
        return "find";
    }

    @GetMapping("/Randomimage")
    public String randomImage() {
        return "Randomimage"; // Randomimage.html 템플릿 반환
    }


}