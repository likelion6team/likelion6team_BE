package com.likelion.likelion6team.domain.user.service;

import com.likelion.likelion6team.domain.user.dto.request.SignUpRequest;
import com.likelion.likelion6team.domain.user.dto.response.SignUpResponse;
import com.likelion.likelion6team.domain.user.entity.User;
import com.likelion.likelion6team.domain.user.exception.UserErrorCode;
import com.likelion.likelion6team.domain.user.mapper.UserMapper;
import com.likelion.likelion6team.domain.user.repository.UserRepository;
import com.likelion.likelion6team.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final UserMapper userMapper;

  public SignUpResponse signUp(SignUpRequest request) {
    if (userRepository.existsByEmail(request.getEmail())) {
      throw new CustomException(UserErrorCode.EMAIL_ALREADY_EXISTS);
    }

    String encodedPassword = passwordEncoder.encode(request.getPassword());

    User user = User.builder()
        .email(request.getEmail())
        .username(request.getUsername())
        .password(encodedPassword)
        .build();

    User savedUser = userRepository.save(user);
    log.info("New user registered: {}", savedUser.getEmail());

    return userMapper.toSignUpResponse(savedUser);
  }
}