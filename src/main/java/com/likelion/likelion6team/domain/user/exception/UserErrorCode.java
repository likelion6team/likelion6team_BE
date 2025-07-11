package com.likelion.likelion6team.domain.user.exception;

import com.likelion.likelion6team.global.exception.model.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum UserErrorCode implements BaseErrorCode {
  EMAIL_ALREADY_EXISTS("USER_4001", "이미 존재하는 사용자 이메일입니다.", HttpStatus.BAD_REQUEST),
  USER_NOT_FOUND("USER_4002", "존재하지 않는 사용자 입니다.", HttpStatus.NOT_FOUND);
  private final String code;
  private final String message;
  private final HttpStatus status;


}
