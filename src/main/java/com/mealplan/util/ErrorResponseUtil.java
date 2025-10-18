package com.mealplan.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.mealplan.dto.ErrorResponseDto;

public class ErrorResponseUtil {
  private ErrorResponseUtil() {} // 인스턴스 생성 X

  public static ResponseEntity<ErrorResponseDto> buildResponse(HttpStatus status, String error, String message) {
    ErrorResponseDto body = ErrorResponseDto.of(false, status.value(), error, message);
    return ResponseEntity.status(status).body(body);
  }
}
