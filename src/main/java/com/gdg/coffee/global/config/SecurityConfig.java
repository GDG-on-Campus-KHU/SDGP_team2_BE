package com.gdg.coffee.global.config;

import com.gdg.coffee.global.security.jwt.JwtAuthenticationFilter;
import com.gdg.coffee.global.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtTokenProvider jwtTokenProvider;

    // HTTP 요청에 대한 필터 체인을 설정
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable) // REST API에서는 CSRF 보호 불필요 (세션을 쓰지 않으므로)
                .httpBasic(AbstractHttpConfigurer::disable) // 브라우저에서 뜨는 기본 인증 팝업을 막음
                .cors(Customizer.withDefaults())
                .formLogin(AbstractHttpConfigurer::disable) // 로그인 form 비활성화 (우린 토큰 기반이니까)
                .sessionManagement(configure -> configure.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // JWT 기반 인증이므로 세션을 아예 생성하지 않음
                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers(
                                        "/login/**",
                                        "/api/users/login/**",
                                        "/api/auth/register",
                                        "/api/auth/login",

                                        "/swagger-ui/**",
                                        "/v3/api-docs/**",
                                        "/h2/**",
                                        "/error",

                                        "/api/cafes/**"
                                ).permitAll()
                                .requestMatchers("/api/member/info").hasRole("USER")
                                //.requestMatchers("/**").permitAll()     // 임시
                                .anyRequest().authenticated()
                )
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
                /**
                 * JwtAuthenticationFilter는 사용자의 요청에 포함된 토큰을 분석하고 인증 객체로 등록해주는 필터.
                 *
                 * UsernamePasswordAuthenticationFilter 전에 실행되도록 설정.
                 * 왜냐하면 스프링 시큐리티는 이 필터에서 인증 객체를 만들어 놓기를 기대하기 때문.
                 * **/
        return httpSecurity.build();
    }
}
