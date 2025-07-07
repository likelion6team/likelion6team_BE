package com.likelion.likelion6team.domain.comment.exception;

import com.likelion.likelion6team.global.exception.model.BaseErrorCode;
import org.springframework.http.HttpStatus;

public enum CommentErrorCode implements BaseErrorCode {
  COMMENT_NOT_FOUND("COMMENT_4001", "존재하지 않는 댓글입니다.", HttpStatus.NOT_FOUND),
  COMMENT_ALREADY_EXISTS("COMMENT_4002", "이미 존재하는 댓글입니다.", HttpStatus.BAD_REQUEST),
  NO_PERMISSION_TO_UPDATE("COMMENT_4003", "댓글 작성자와 회원 정보가 일치하지 않습니다.", HttpStatus.NOT_FOUND),
  INVALID_FOOD_ID_FOR_COMMENT_UPDATE("COMMENT_4004", "댓글 게시글 정보와 게시글 정보가 일치하지 않습니다.", HttpStatus.NOT_FOUND);


  private final String code;
  private final String message;
  private final HttpStatus status;
}
