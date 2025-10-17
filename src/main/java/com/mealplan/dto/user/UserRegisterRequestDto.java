package com.mealplan.dto.user;

import com.mealplan.entity.user.User;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRegisterRequestDto {

    @NotBlank(message = "아이디는 필수 입력값입니다.")
    @Size(min = 4, max = 10, message = "아이디는 4자 이상, 10자 이하로 입력해주세요.")
    private String userId;

    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    @Size(min = 4, max = 20, message = "비밀번호는 4자 이상, 20자 이하로 입력해주세요.")
    private String userPw;

    @NotBlank(message = "이름은 필수 입력값입니다.")
    private String userName;

    @NotBlank(message = "휴대폰 번호는 필수 입력값입니다.")
    @Size(min = 10, max = 11, message = "휴대폰 번호는 10~11자리여야 합니다.")
    @Pattern(regexp = "\\d{10,11}", message = "휴대폰 번호는 숫자만 입력해야 합니다.")
    private String userPhone;

    public User toEntity(String encodedPassword) {
      return User.builder()
        .userId(this.userId)
        .userPw(encodedPassword)
        .userName(this.userName)
        .userPhone(this.userPhone)
        .build();
    }
}
