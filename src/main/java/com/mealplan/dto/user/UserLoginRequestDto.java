package com.mealplan.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserLoginRequestDto {
  @NotBlank(message = "아이디는 필수 입력값입니다.")
  @Size(min = 4, max = 10, message = "아이디는 4자 이상, 20자 이하로 입력해주세요.")
  private String userId;

  @NotBlank(message = "비밀번호는 필수 입력값입니다.")
  @Size(min = 4, max = 20, message = "비밀번호는 4자 이상, 20자 이하로 입력해주세요.")
  private String userPw;
}
