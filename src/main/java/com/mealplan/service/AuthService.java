package com.mealplan.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mealplan.dto.SimpleResponseDto;
import com.mealplan.dto.user.UserRegisterRequestDto;
import com.mealplan.entity.user.User;
import com.mealplan.exception.user.DuplicateUserException;
import com.mealplan.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  
  // 회원가입
  public SimpleResponseDto register(UserRegisterRequestDto dto) {
    String userId = dto.getUserId();
    String rawUserPw = dto.getUserPw();
    String userName = dto.getUserName();
    String userPhone = dto.getUserPhone();

    // 아이디 중복 검사 
    if(userRepository.existsByUserId(userId)) {
      throw new DuplicateUserException(DuplicateUserException.ErrorType.LOGIN_ID, "이미 사용 중인 아이디입니다.");
    }

    // 휴대폰 번호 중복 검사
    if (userRepository.existsByUserPhone(userPhone)) {
      throw new DuplicateUserException(DuplicateUserException.ErrorType.PHONE_NUMBER, "이미 가입된 휴대폰 번호입니다.");
    }

    // 비밀번호 암호화
    String encodedPassword = passwordEncoder.encode(rawUserPw);
    log.info("encodedPassword length : {}", encodedPassword.length());

    // 회원 저장
    User newUser = dto.toEntity(encodedPassword);
    userRepository.save(newUser);

    return new SimpleResponseDto(true, "회원가입 완료");
  }
}
