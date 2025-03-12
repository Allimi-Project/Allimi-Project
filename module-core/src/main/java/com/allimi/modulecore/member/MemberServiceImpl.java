package com.allimi.modulecore.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
@Slf4j
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public void save(SignUpDto signDto) {

        // DTO -> Entity 변환 및 비밀번호 암호화
        Member member = Member.builder()
                .username(signDto.getUsername())
                .email(signDto.getEmail())
                .password(passwordEncoder.encode(signDto.getPassword())) // 비밀번호 암호화
                .build();

        // 회원 정보 저장
        memberRepository.save(member);
    }
}
