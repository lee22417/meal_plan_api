package com.mealplan.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ErrorResponseDto { // 에러 응답 dto
    private boolean success;
    private String message;
    private int status;
    private String error;
    private LocalDateTime timestamp;

    // 공용 생성 메서드
    public static ErrorResponseDto toEntity(boolean success, String message, int status, String error) {
      return ErrorResponseDto.builder()
        .success(success)
        .message(message)
        .status(status)
        .error(error)
        .timestamp(LocalDateTime.now())
        .build();
    }
}
