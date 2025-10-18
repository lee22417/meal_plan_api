package com.mealplan.exception.auth;

// 로그인 실패
public class LoginFailedException extends RuntimeException {
  private final ErrorType errorType; // ENUM으로 구분 가능

  public enum ErrorType { USER, USER_PW }

  public LoginFailedException(ErrorType errorType, String message) {
      super(message);
      this.errorType = errorType;
  }

  public ErrorType getErrorType() {
      return errorType;
  }
  
}
