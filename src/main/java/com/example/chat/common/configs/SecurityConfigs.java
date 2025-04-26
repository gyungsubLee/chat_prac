package com.example.chat.common.configs;

import com.example.chat.common.auth.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SecurityConfigs {

    private final JwtAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain myFilter(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable) // csrf 비활성화
                .httpBasic(AbstractHttpConfigurer::disable) // HTTP Basic 비활성화
                // 특정 url 패턴에 대해서는 Authentication 객체 요구하지 않음. ( 인증 처리 제외 )
                .authorizeHttpRequests(a -> a.requestMatchers("/members/create", "/members/doLogin").permitAll().anyRequest().authenticated())
                .sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))  // 세션 방식을 사용하지 않겠다 라는 의미
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)  // 커스텀 필터(jwtAuthFilter)로 가서 유저 인증 정보를 확인 후 chain.doFiter(..)를 통해 돌아온다.
                .build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000")); // 해당 URL 요청 허용
        configuration.setAllowedMethods(List.of("*")); // 모든 HTTP 메서드 허용
        configuration.setAllowedHeaders(List.of("*")); // 모든 헤더값 허용
        configuration.setAllowCredentials(true); // 자격 증명 허용

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);  // 모든 url 패턴에 대해 cors 허용
        return source;
    }

    // 기본 알고리즘: bcrypt
    @Bean
    public PasswordEncoder makePassword() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
