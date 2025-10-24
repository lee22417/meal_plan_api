package com.mealplan.controller.auth;

import com.mealplan.dto.DataResponseDto;
import com.mealplan.dto.SimpleResponseDto;
import com.mealplan.dto.user.UserLoginRequestDto;
import com.mealplan.dto.user.UserRegisterRequestDto;
import com.mealplan.exception.jwt.JwtNotFoundException;
import com.mealplan.security.CustomUserDetails;
import com.mealplan.service.auth.AuthService;
import com.mealplan.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
  private final AuthService authService;
  private final JwtUtil jwtUtil;

  // 회원가입
  @PostMapping("/signup")
  public SimpleResponseDto signup(@Valid @RequestBody UserRegisterRequestDto dto) {
    return authService.signup(dto);
  }

  // 로그인
  @PostMapping("/login")
  public DataResponseDto login(@Valid @RequestBody UserLoginRequestDto dto) {
    return authService.login(dto);
  }

  // 로그아웃
  @PostMapping("/logout")
  public SimpleResponseDto logout(HttpServletRequest request) {
    String token = jwtUtil.extractToken(request);
    if (token == null) {
      throw new JwtNotFoundException("JWT 조회 실패 (header)");
    }
    return authService.logout(token);
  }

  // jwt token 조회
  @GetMapping("/token")
  public DataResponseDto getTokenInfo(@AuthenticationPrincipal CustomUserDetails userDetails) {
    Map<String, Object> data =
        Map.of("uid", userDetails.getUId(), "userId", userDetails.getUserId());
    return new DataResponseDto(true, "조회 완료", data);
  }
}
