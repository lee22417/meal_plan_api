package com.mealplan.config;

import com.mealplan.constants.RoleConstants;
import com.mealplan.filter.JwtAuthenticationFilter;
import com.mealplan.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
  private final JwtUtil jwtUtil;

  // 회원 비밀번호 암호화
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(10);
  }

  // JWT 필터 bean 등록
  @Bean
  public JwtAuthenticationFilter jwtAuthenticationFilter() {
    return new JwtAuthenticationFilter(jwtUtil);
  }

  // jwt token 검증
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf(csrf -> csrf.disable())
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(
            auth ->
                auth
                    // 인증 없이 접근 가능
                    .requestMatchers("/auth/login", "/auth/signup")
                    .permitAll()
                    // 인증 + 권한 검사 (admin 접근 가능)
                    .requestMatchers("/admin/**")
                    .hasRole(RoleConstants.ADMIN)
                    // 인증 + 권한 검사 (user, admin 접근 가능)
                    .requestMatchers("/user/**")
                    .hasAnyRole(RoleConstants.ADMIN, RoleConstants.USER)
                    // 인증만 되면 접근 가능 (role 상관없음)
                    .anyRequest()
                    .authenticated())
        // JWT 필터 등록
        .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }
}
