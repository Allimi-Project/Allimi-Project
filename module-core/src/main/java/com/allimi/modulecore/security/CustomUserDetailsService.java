package com.allimi.modulecore.security;

import com.allimi.modulecore.member.Member;
import com.allimi.modulecore.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Member member  = memberRepository.findByEmail(username);

        if (member != null) {
            return new CustomUserDetails(member);
        } else throw new UsernameNotFoundException("사용자를 찾을수 없습니다.");
    }
}
