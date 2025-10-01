package com.mealplan.exception.user;

// user 에러
public class DuplicateUserException extends RuntimeException {
  private final ErrorType errorType; // ENUM으로 구분 가능

  public enum ErrorType { LOGIN_ID, PHONE_NUMBER }

  public DuplicateUserException(ErrorType errorType, String message) {
      super(message);
      this.errorType = errorType;
  }

  public ErrorType getErrorType() {
      return errorType;
  }
}
