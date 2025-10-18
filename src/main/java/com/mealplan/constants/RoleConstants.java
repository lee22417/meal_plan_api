package com.mealplan.constants;

// JWT + JwtAuthenticationFilter + filterChain 접근 권한 설정
// 예) 회원/관리자 접근, 회원 등급별 접근, 관리자 직위별 접근
public class RoleConstants {
  public static final String ADMIN = "ADMIN"; // 관리자
  public static final String USER = "USER"; // 회원
}
