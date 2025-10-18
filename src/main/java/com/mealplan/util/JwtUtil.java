package com.mealplan.util;

import java.util.Date;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import com.mealplan.config.JwtConfig;

@RequiredArgsConstructor
@Service
public class JwtUtil { // jwt util
  private final JwtConfig jwtConfig;
  
  // jwt token 발급
  public String generateToken(String uId, String userId, String userName) {
    return Jwts.builder()
      .setSubject(uId)
      .claim("userId", userId)
      .claim("userName", userName)
      .setIssuedAt(new Date())
      .setExpiration(new Date(System.currentTimeMillis() + jwtConfig.getExpirationMs()))
      .signWith(jwtConfig.getSecretKey(), SignatureAlgorithm.HS256)
      .compact();
  }
}
