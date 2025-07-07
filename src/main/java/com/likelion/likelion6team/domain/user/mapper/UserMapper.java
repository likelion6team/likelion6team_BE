package com.likelion.likelion6team.domain.user.mapper;

import com.likelion.likelion6team.domain.user.dto.response.SignUpResponse;
import com.likelion.likelion6team.domain.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

  public SignUpResponse toSignUpResponse(User user) {
    return SignUpResponse.builder()
        .userId(user.getId())
        .username(user.getUsername())
        .email(user.getEmail())
        .build();
  }


}