package com.mealplan.service.auth;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mealplan.config.JwtConfig;
import com.mealplan.dto.DataResponseDto;
import com.mealplan.dto.SimpleResponseDto;
import com.mealplan.dto.user.UserLoginRequestDto;
import com.mealplan.dto.user.UserRegisterRequestDto;
import com.mealplan.entity.User;
import com.mealplan.entity.UserSession;
import com.mealplan.exception.auth.DuplicateUserException;
import com.mealplan.exception.auth.LoginFailedException;
import com.mealplan.repository.UserRepository;
import com.mealplan.repository.UserSessionRepository;
import com.mealplan.util.JwtUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
  private final UserRepository userRepository;
  private final UserSessionRepository userSessionRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtConfig jwtConfig;
  private final JwtUtil jwtUtil;
  
  // 회원가입
  public SimpleResponseDto register(UserRegisterRequestDto dto) {
    String userId = dto.getUserId();
    String rawUserPw = dto.getUserPw();
    String userName = dto.getUserName();
    String userPhone = dto.getUserPhone();

    // 아이디 중복 검사 
    if(userRepository.existsByUserId(userId)) {
      throw new DuplicateUserException(DuplicateUserException.ErrorType.USER_ID, "이미 사용 중인 아이디입니다.");
    }

    // 휴대폰 번호 중복 검사
    if (userRepository.existsByUserPhone(userPhone)) {
      throw new DuplicateUserException(DuplicateUserException.ErrorType.USER_PHONE, "이미 가입된 휴대폰 번호입니다.");
    }

    // 비밀번호 암호화
    String encodedPassword = passwordEncoder.encode(rawUserPw);
    log.info("encodedPassword length : {}", encodedPassword.length());

    // 회원 저장
    User newUser = dto.toEntity(encodedPassword);
    userRepository.save(newUser);

    return new SimpleResponseDto(true, "회원가입 완료");
  }

  // 로그인
  public DataResponseDto login(UserLoginRequestDto dto) {
    String userId = dto.getUserId();
    String rawUserPw = dto.getUserPw();

    // 회원 조회
    User user = userRepository.findByUserId(userId)
      .orElseThrow(() -> new LoginFailedException(LoginFailedException.ErrorType.USER, "아이디 또는 비밀번호가 올바르지 않습니다."));

    // 비밀번호 검증
    if(!passwordEncoder.matches(rawUserPw, user.getUserPw())) {
      throw new LoginFailedException(LoginFailedException.ErrorType.USER_PW, "아이디 또는 비밀번호가 올바르지 않습니다.");
    }

    // jwt token 발급
    String token = jwtUtil.generateToken(user.getUserId(), user.getUserId(), user.getUserName());

    // jwt token DB에 저장
    UserSession session = UserSession.of(token, jwtConfig.getExpirationMs());
    userSessionRepository.save(session);

    Map<String, Object> data = new HashMap<>();
    data.put("token", token);
    return new DataResponseDto(true, "로그인 완료", data);
  }

}
