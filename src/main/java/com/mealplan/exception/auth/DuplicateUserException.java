package com.mealplan.exception.auth;

// 회원 중복 에러
public class DuplicateUserException extends RuntimeException {
  private final ErrorType errorType; // ENUM으로 구분 가능

  public enum ErrorType { USER_ID, USER_PHONE }

  public DuplicateUserException(ErrorType errorType, String message) {
      super(message);
      this.errorType = errorType;
  }

  public ErrorType getErrorType() {
      return errorType;
  }
}
