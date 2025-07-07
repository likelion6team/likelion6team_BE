package com.likelion.likelion6team.domain.auth.mapper;
import com.likelion.likelion6team.domain.auth.dto.response.LoginResponse;
import com.likelion.likelion6team.domain.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class AuthMapper {

  public LoginResponse toLoginResponse(User user, String accessToken, Long expirationTime) {
    return LoginResponse.builder()
        .accessToken(accessToken)
        .userId(user.getId())
        .email(user.getEmail())
        .role(user.getRole())
        .expirationTime(expirationTime)
        .build();
  }
}
