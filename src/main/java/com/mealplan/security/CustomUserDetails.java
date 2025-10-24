package com.mealplan.security;

import java.util.Collection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

// Spring Security에서 사용자 인증(JWT) 정보를 담는 커스텀 UserDetails
@Getter
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {
  private final String uId; // user pk (커스텀)
  private final String userId; // 회원 아이디 (커스텀)
  private final Collection<? extends GrantedAuthority> authorities; // 권한

  @Override
  public String getUsername() {
    return uId; // principal 이름 (Spring Security에서 인증된 사용자를 식별할때 사용)
  }

  @Override
  public String getPassword() {
    return null; // JWT라서 패스워드 검증 필요 없음
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
