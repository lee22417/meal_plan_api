package com.mealplan.filter;

import com.mealplan.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
  private final JwtUtil jwtUtil;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    String authHeader = request.getHeader("Authorization");

    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      filterChain.doFilter(request, response); // 토큰 없으면 다음 필터로
      return;
    }

    String token = authHeader.substring(7);

    try {
      // JWT 검증 및 payload 추출
      Claims claims = jwtUtil.validateTokenAndStatus(token);
      String uId = claims.getSubject();
      // String userId = claims.get("userId", String.class);
      List<String> roles = claims.get("roles", List.class); // JWT 발급 시 roles

      // roles -> GrantedAuthority 변환
      List<GrantedAuthority> authorities =
          roles != null
              ? roles.stream()
                  .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                  .collect(Collectors.toList())
              : List.of();

      // UsernamePasswordAuthenticationToken = 인증 정보 객체
      // 인자 -> 사용자 식별값, 패스워드, 권한 정보
      UsernamePasswordAuthenticationToken authentication =
          new UsernamePasswordAuthenticationToken(uId, null, authorities);
      // request 정보 추가 (ip, session id 등)
      authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

      // SecurityContext에 저장
      // controller, service -> SecurityContextHolder.getContext().getAuthentication()로 조회
      SecurityContextHolder.getContext().setAuthentication(authentication);
      log.debug("[JWT Filter] 인증 성공: uId={}", uId);
    } catch (JwtException e) {
      log.warn("[JWT Filter] 인증 실패: {}", e.getMessage());
      throw e; // JwtExceptionHandler 에서 처리
    }

    filterChain.doFilter(request, response);
  }
}
