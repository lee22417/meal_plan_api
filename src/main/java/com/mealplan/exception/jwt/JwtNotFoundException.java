package com.mealplan.exception.jwt;

  // JWT 조회 실패
public class JwtNotFoundException extends JwtException {
  public JwtNotFoundException(String message) {
    super(message);
  }
}