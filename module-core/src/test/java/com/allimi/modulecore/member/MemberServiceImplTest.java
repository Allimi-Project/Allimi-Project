package com.allimi.modulecore.member;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MemberServiceImplTest {

    @Mock
    private MemberRepository memberRepository; // 가짜 MemberRepository

    @Mock
    private PasswordEncoder passwordEncoder; // 가짜 PasswordEncoder

    @InjectMocks
    private MemberServiceImpl memberService; // 테스트 대상 (Mock을 주입)

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("회원가입 - 비밀번호 암호화 후 저장")
    void saveMemberWithEncodedPassword() {
        // Given (테스트 데이터 준비)
        SignUpDto signUpDto = new SignUpDto("testuser", "test@example.com", "password123");
        String encodedPassword = "encodedPassword123"; // 암호화된 비밀번호 (Mock 처리)

        // Mock 설정: passwordEncoder.encode()가 호출되면 encodedPassword를 반환하도록 설정
        when(passwordEncoder.encode(signUpDto.getPassword())).thenReturn(encodedPassword);

        // When (실제 실행)
        memberService.save(signUpDto);

        // Then (검증)
        // memberRepository.save()가 1번 호출되었는지 확인
        verify(memberRepository, times(1)).save(any(Member.class));

        // 실제 저장된 Member 객체를 캡처하여 검증
        ArgumentCaptor<Member> captor = ArgumentCaptor.forClass(Member.class);
        verify(memberRepository).save(captor.capture());

        Member savedMember = captor.getValue();
        assertThat(savedMember.getUsername()).isEqualTo(signUpDto.getUsername()); // 유저네임 검증
        assertThat(savedMember.getEmail()).isEqualTo(signUpDto.getEmail()); // 이메일 검증
        assertThat(savedMember.getPassword()).isEqualTo(encodedPassword); // 비밀번호가 암호화되었는지 검증
    }

}