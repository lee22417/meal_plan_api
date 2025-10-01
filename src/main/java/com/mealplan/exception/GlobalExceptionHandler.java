package com.mealplan.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.mealplan.dto.ErrorResponseDto;
import com.mealplan.exception.user.DuplicateUserException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

// 모든 예외를 공통으로 처리할 수 있도록 @RestControllerAdvice 사용
@RestControllerAdvice
public class GlobalExceptionHandler {

  // controller @valid 에러
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ErrorResponseDto handleValidationException(MethodArgumentNotValidException ex) {
    String message = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
    return ErrorResponseDto.toEntity(false, message, HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase());
  }

  // ---- 사용자 정의 exception

  // user 관련 에러
  @ExceptionHandler(DuplicateUserException.class)
  public ErrorResponseDto handleDuplicateUser(DuplicateUserException ex) {
    // 예시 코드
    /* 
    if(ex.getErrorType() == DuplicateUserException.ErrorType.LOGIN_ID) {
    } else {
    }
    */

    return ErrorResponseDto.toEntity(false, ex.getMessage(), 
      HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase());
  }
}
