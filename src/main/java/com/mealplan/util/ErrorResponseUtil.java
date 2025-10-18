package com.mealplan.util;

import com.mealplan.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ErrorResponseUtil {
  private ErrorResponseUtil() {} // 인스턴스 생성 X

  public static ResponseEntity<ErrorResponseDto> buildResponse(
      HttpStatus status, String error, String message) {
    ErrorResponseDto body = ErrorResponseDto.of(false, status.value(), error, message);
    return ResponseEntity.status(status).body(body);
  }
}
