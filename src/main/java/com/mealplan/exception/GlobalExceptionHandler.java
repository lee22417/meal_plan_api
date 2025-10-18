package com.mealplan.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.mealplan.dto.ErrorResponseDto;

// 모든 예외를 공통으로 처리할 수 있도록 @RestControllerAdvice 사용
@RestControllerAdvice
public class GlobalExceptionHandler {

  // controller @valid 에러
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ErrorResponseDto handleValidationException(MethodArgumentNotValidException ex) {
    String message = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
    return ErrorResponseDto.of(false, message, HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase());
  }
}
