package com.mealplan.exception.auth;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.mealplan.dto.ErrorResponseDto;

// auth 관련 예외 처리
// basePackages = "com.mealplan.controller.auth" -> 해당 패키지 내 컨트롤러에서 던진 예외만 처리
@RestControllerAdvice(basePackages = "com.mealplan.controller.auth")
public class AuthExceptionHandler {

  // 회원 중복 에러
  @ExceptionHandler(DuplicateUserException.class)
  public ErrorResponseDto handleDuplicateUser(DuplicateUserException ex) {
    // 예시 코드
    /* 
    if(ex.getErrorType() == DuplicateUserException.ErrorType.USER_ID) {
    } else {
    }
    */

    return ErrorResponseDto.of(false, ex.getMessage(), 
      HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase());
  }

  // 로그인 실패
  @ExceptionHandler(LoginFailedException.class)
  public ErrorResponseDto handleLoginFalied(LoginFailedException ex) {
    return ErrorResponseDto.of(false, ex.getMessage(), 
      HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase());
  }
}
