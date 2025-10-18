package com.mealplan.exception.jwt;

// JWT 자체 만료
public class JwtExpiredException extends JwtException {
  public JwtExpiredException(String message) {
    super(message);
  }
}
