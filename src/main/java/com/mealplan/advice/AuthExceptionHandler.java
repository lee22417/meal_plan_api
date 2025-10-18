package com.mealplan.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.mealplan.dto.ErrorResponseDto;
import com.mealplan.exception.auth.DuplicateUserException;
import com.mealplan.exception.auth.LoginFailedException;
import static com.mealplan.util.ErrorResponseUtil.buildResponse;

// auth 관련 예외 처리
// basePackages = "com.mealplan.controller.auth" -> 해당 패키지 내 컨트롤러에서 던진 예외만 처리
@RestControllerAdvice(basePackages = "com.mealplan.controller.auth")
public class AuthExceptionHandler {
  // 회원 중복 에러
  @ExceptionHandler(DuplicateUserException.class)
  public ResponseEntity<ErrorResponseDto> handleDuplicateUser(DuplicateUserException e) {
    // 예시 코드
    /* 
    if(ex.getErrorType() == DuplicateUserException.ErrorType.USER_ID) {
    } else {
    }
    */
    return buildResponse(HttpStatus.BAD_REQUEST, "DUPLICATE_USER", e.getMessage());
  }

  // 로그인 실패
  @ExceptionHandler(LoginFailedException.class)
  public ResponseEntity<ErrorResponseDto> handleLoginFalied(LoginFailedException e) {
    return buildResponse(HttpStatus.BAD_REQUEST, "LOGIN_FAILED", e.getMessage());
  }
}
