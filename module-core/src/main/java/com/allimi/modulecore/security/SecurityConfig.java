package com.allimi.modulecore.security;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // 암호화 메서드
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http // 경로 접근 권한 설정
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/", "/login", "/join").permitAll() // 로그인 없이 접근 가능
                        .requestMatchers("/page").authenticated() // 로그인한 사용자만 접근 가능
                        .anyRequest().authenticated()
                );
        http // 로그인 페이지로 리다이렉션 설정
                .formLogin((auth) -> auth
                        .loginPage("/login") // 커스텀 로그인 페이지 설정
                        .loginProcessingUrl("/login")
                        .usernameParameter("email") // 기본 "username"을 "email"로 변경
                        .defaultSuccessUrl("/page", true) // 로그인 성공 시 이동할 페이지
                        .permitAll()
                );

        http
                .csrf((auth) -> auth.disable()); // 개발환경에서 csrf토큰 미사용

        http
                .sessionManagement((auth) -> auth
                        .maximumSessions(1) // 하나의 아이디에 대한 다중로그인 허용 개수
                        .maxSessionsPreventsLogin(true)); // 초과시 새로운 로그인 차단

        return http.build();
    }
}
