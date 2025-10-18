package com.mealplan.config;

import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

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
