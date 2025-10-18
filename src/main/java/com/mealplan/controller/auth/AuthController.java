package com.mealplan.controller.auth;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mealplan.dto.DataResponseDto;
import com.mealplan.dto.SimpleResponseDto;
import com.mealplan.dto.user.UserLoginRequestDto;
import com.mealplan.dto.user.UserRegisterRequestDto;
import com.mealplan.service.auth.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
  private final AuthService authService;

  // 회원가입
  @PostMapping("/register")
  public SimpleResponseDto register(@Valid @RequestBody UserRegisterRequestDto dto) {
    return authService.register(dto); 
  }
  
  // 로그인
  @PostMapping("/login")
  public DataResponseDto login(@Valid @RequestBody UserLoginRequestDto dto) {
    return authService.login(dto); 
  }
}
