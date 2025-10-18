package com.mealplan.util;

import com.mealplan.config.JwtConfig;
import com.mealplan.entity.UserSession;
import com.mealplan.exception.jwt.JwtExpiredException;
import com.mealplan.exception.jwt.JwtInvalidException;
import com.mealplan.exception.jwt.JwtNotFoundException;
import com.mealplan.exception.jwt.JwtRevokedException;
import com.mealplan.repository.UserSessionRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Slf4j
public class JwtUtil { // JWT util
  private final JwtConfig jwtConfig;
  private final UserSessionRepository userSessionRepository;

  // JWT 검증 및 parse payload
  private Claims parseClaims(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(jwtConfig.getSecretKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  // JWT 발급
  public String generateToken(String uId, String userId, List<String> roles) {
    return Jwts.builder()
        .setSubject(uId)
        .claim("userId", userId)
        .claim("roles", roles)
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + jwtConfig.getExpirationMs()))
        .signWith(jwtConfig.getSecretKey(), SignatureAlgorithm.HS256)
        .compact();
  }

  // JWT 자체 검증 + DB 확인
  @Transactional(readOnly = true)
  public Claims validateTokenAndStatus(String token) {
    try {
      Claims claims = parseClaims(token); // JWT 자체 검증

      UserSession session =
          userSessionRepository
              .findByToken(token)
              .orElseThrow(() -> new JwtNotFoundException("JWT 조회 실패 (DB)")); // JWT DB 조회 실패

      // JWT DB 만료
      if (session.getExpiredAt().isBefore(LocalDateTime.now())) {
        throw new JwtRevokedException("JWT 만료 (DB): " + session.getExpiredAt());
      }

      return claims;
    } catch (ExpiredJwtException e) { // JWT 자체 만료
      throw new JwtExpiredException("JWT 자체 만료: " + e.getMessage());
    } catch (JwtException | IllegalArgumentException e) { // / JWT 서명/형식 오류
      throw new JwtInvalidException("유효하지 않은 JWT: " + e.getMessage());
    } catch (Exception e) { // 그 외 예외
      throw new JwtException("JWT 검증 중 예기치 못한 오류", e);
    }
  }

  // JWT 만료 시키기 (Revoked) (실제 토큰은 클라이언트에 있으므로 DB 기반으로 처리)
  @Transactional
  public void expireTokenInDb(String token) {
    userSessionRepository
        .findByToken(token)
        .ifPresent(
            session -> {
              session.setExpiredAt(LocalDateTime.now());
              userSessionRepository.save(session);
              log.info("JWT 강제 만료됨: {}", token);
            });
  }

  // Date에서 LoalDateTime으로 변환
  // 사용 예시) LocalDateTime jwtExp = toLocalDateTime(claims.getExpiration());
  private LocalDateTime toLocalDateTime(Date date) {
    if (date == null) {
      return null;
    }
    return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
  }
}
