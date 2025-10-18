package com.mealplan.exception.jwt;

// JWT DB 만료 처리
public class JwtRevokedException extends JwtException {
  public JwtRevokedException(String message) {
    super(message);
  }
}