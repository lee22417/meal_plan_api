package com.mealplan.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.mealplan.dto.ErrorResponseDto;
import static com.mealplan.util.ErrorResponseUtil.buildResponse;

// 모든 예외를 공통으로 처리할 수 있도록 @RestControllerAdvice 사용
@RestControllerAdvice
public class GlobalExceptionHandler {
  // controller @valid 에러
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponseDto> handleValidationException(MethodArgumentNotValidException e) {
    String message = e.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
    return buildResponse(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.getReasonPhrase(), message);
  }
}
