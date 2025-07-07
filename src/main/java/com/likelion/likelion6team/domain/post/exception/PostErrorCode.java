package com.likelion.likelion6team.domain.post.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import com.likelion.likelion6team.global.exception.model.BaseErrorCode;

@Getter
@AllArgsConstructor
public enum PostErrorCode implements BaseErrorCode {
  POST_ERROR_FOUND("POST_4041", "해당 게시글을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
  INVALID_POST_TITLE("POST_4001", "게시글 제목은 필수입니다.", HttpStatus.BAD_REQUEST),
  INVALID_POST_CONTENT("POST_4002", "게시글 제목은 필수입니다.", HttpStatus.BAD_REQUEST),
  TITLE_TOO_LONG("POST_4003", "게시글 제목은 10자 이하로 작성해야합니다.", HttpStatus.BAD_REQUEST),
  NO_PERMISSION_TO_UPDATE("POST_4004", "게시글 작성자와 일치하지 않습니다.", HttpStatus.NOT_FOUND) ;


  private final String code;
  private final String message;
  private final HttpStatus status;
}
