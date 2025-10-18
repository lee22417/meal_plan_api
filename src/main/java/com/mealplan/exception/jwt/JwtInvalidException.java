package com.mealplan.exception.jwt;

// 유효하지 않은 JWT
public class JwtInvalidException extends JwtException {
  public JwtInvalidException(String message) {
    super(message);
  }
}