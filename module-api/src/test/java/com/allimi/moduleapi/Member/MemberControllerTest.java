package com.allimi.moduleapi.Member;

import com.allimi.modulecore.member.MemberRepository;
import com.allimi.modulecore.member.MemberService;
import com.allimi.modulecore.member.SignUpDto;
import com.allimi.modulecore.security.CustomUserDetailsService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest // 전체 컨텍스트 로드
@AutoConfigureMockMvc(addFilters = false) // Spring Security 비활성화
class MemberControllerTest {

    @Autowired // Http 요청 수행 가짜 객체 생성
    private MockMvc mockMvc;

    @MockBean // 서비스 가짜 구현체 생성
    private MemberService memberService;

    @Test
    @DisplayName("회원 가입 성공")
    void signUpSuccess() throws Exception {
        SignUpDto signUpDto = new SignUpDto("testuser", "test@example.com", "password123");

        mockMvc.perform(post("/signup") // POST /signup 요청
                        .param("username", signUpDto.getUsername())  // username 전달
                        .param("email", signUpDto.getEmail())  // email 전달
                        .param("password", signUpDto.getPassword()))  // password 전달
                .andExpect(status().is3xxRedirection())  // 회원가입 성공 시 리다이렉트 응답(3xx) 기대
                .andExpect(redirectedUrl("/login"));  // 성공 후 /login 페이지로 리다이렉트되는지 검증

        verify(memberService, times(1)).save(any(SignUpDto.class));  // memberService의 save()가 1회 호출되었는지 확인
    }

    @Test
    @DisplayName("회원 가입 실패 - 유효성 검사 실패")
    void signUpValidationFail() throws Exception {
        mockMvc.perform(post("/signup")
                        .param("username", "") // username이 비어 있음
                        .param("email", "alkdsf@gmail.com") //  유효한 이메일 형식
                        .param("password", "qwer123")) // 유효한 비밀번호
                .andExpect(status().isBadRequest());
    }

}