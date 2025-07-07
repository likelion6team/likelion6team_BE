package com.likelion.likelion6team.domain.post.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
@AllArgsConstructor
public class PostErrorCode implements BaseErrorCode {
  POST_ERROR_FOUND("POST_4041", "해당 게시글을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
  INVALID_POST_TITLE("POST_4001", "게시글 제목은 필수입니다.", HttpStatus.BAD_REQUEST),
  INVALID_POST_CONTENT("POST_4002", "게시글 제목은 필수입니다.", HttpStatus.BAD_REQUEST),
  TITLE_TOO_LONG("POST_4003", "게시글 제목은 10자 이하로 작성해야합니다.", HttpStatus.BAD_REQUEST);


  private final String code;
  private final String message;
  private final HttpStatus status;
}
