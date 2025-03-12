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
                        .requestMatchers("/**").permitAll() // 로그인 없이 접근 가능
                        .requestMatchers("/page").authenticated() // 로그인한 사용자만 접근 가능
                        .anyRequest().authenticated()
                );
        http // 로그인 페이지로 리다이렉션 설정
                .formLogin((auth) -> auth
                        .loginPage("/login") // 커스텀 로그인 페이지 설정
                        .defaultSuccessUrl("/page", true) // 로그인 성공 시 이동할 페이지
                        .permitAll()
                );

        http
                .csrf((auth) -> auth.disable()); // 개발환경에서 csrf토큰 미사용

        return http.build();
    }
}
