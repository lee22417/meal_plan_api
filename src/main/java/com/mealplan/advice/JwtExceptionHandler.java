package com.mealplan.advice;

import static com.mealplan.util.ErrorResponseUtil.buildResponse;

import com.mealplan.dto.ErrorResponseDto;
import com.mealplan.exception.jwt.JwtException;
import com.mealplan.exception.jwt.JwtExpiredException;
import com.mealplan.exception.jwt.JwtInvalidException;
import com.mealplan.exception.jwt.JwtNotFoundException;
import com.mealplan.exception.jwt.JwtRevokedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class JwtExceptionHandler {
  @ExceptionHandler(JwtException.class)
  public ResponseEntity<ErrorResponseDto> handleGeneric(JwtException e) {
    return buildResponse(HttpStatus.UNAUTHORIZED, "TOKEN_ERROR", e.getMessage());
  }

  // JWT 자체 만료
  @ExceptionHandler(JwtExpiredException.class)
  public ResponseEntity<ErrorResponseDto> handleExpired(JwtExpiredException e) {
    return buildResponse(HttpStatus.UNAUTHORIZED, "TOKEN_EXPIRED", e.getMessage());
  }

  // 유효하지 않은 JWT
  @ExceptionHandler(JwtInvalidException.class)
  public ResponseEntity<ErrorResponseDto> handleInvalid(JwtInvalidException e) {
    return buildResponse(HttpStatus.UNAUTHORIZED, "TOKEN_INVALID", e.getMessage());
  }

  // JWT 조회 실패
  @ExceptionHandler(JwtNotFoundException.class)
  public ResponseEntity<ErrorResponseDto> handleNotFound(JwtNotFoundException e) {
    return buildResponse(HttpStatus.UNAUTHORIZED, "TOKEN_NOT_FOUND", e.getMessage());
  }

  // JWT DB 만료 처리
  @ExceptionHandler(JwtRevokedException.class)
  public ResponseEntity<ErrorResponseDto> handleRevoked(JwtRevokedException e) {
    return buildResponse(HttpStatus.UNAUTHORIZED, "TOKEN_REVOKED", e.getMessage());
  }
}
