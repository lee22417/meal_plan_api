package com.mealplan.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

@Configuration
public class JwtConfig { // jwt 설정
  @Value("${jwt.secret}")
  private String secret; // jwt secret key

  @Value("${jwt.expiration-ms}") 
  private long expirationMs; // jwt 만료 시간

  public SecretKey getSecretKey() {
    return Keys.hmacShaKeyFor(secret.getBytes());
  }

  public long getExpirationMs() {
    return expirationMs;
  }
}
