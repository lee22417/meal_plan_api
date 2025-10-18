package com.mealplan.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ErrorResponseDto { // 에러 응답 dto
  private boolean success;
  private String message;
  private int status;
  private String error;
  private LocalDateTime timestamp;

  // 공용 생성 메서드
  public static ErrorResponseDto of(boolean success, int status, String error, String message) {
    return ErrorResponseDto.builder()
      .success(success)
      .status(status)
      .error(error)
      .message(message)
      .timestamp(LocalDateTime.now())
      .build();
  }
}
