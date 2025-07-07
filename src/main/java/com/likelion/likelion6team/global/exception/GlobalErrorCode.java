package com.likelion.likelion6team.global.exception;


import com.likelion.likelion6team.global.exception.model.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum GlobalErrorCode implements BaseErrorCode {
  INVALID_INPUT_VALUE("GLOBAL001", "유효하지 않은 입력입니다.", HttpStatus.BAD_REQUEST),
  RESOURCE_NOT_FOUND("GLOBAL002", "요청한 리소스를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
  NOT_FOUND_LIKE("LIKE_001", "좋아요를 누르지 않았습니다.", HttpStatus.BAD_REQUEST),
  NOT_FOUND_FOOD("FOOD_NOT", "음식을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
  ALREADY_LIKED("LIKE_NOT", "좋아요를 이미 누르셨습니다.", HttpStatus.BAD_REQUEST),
  REVIEW_NOT_FOUND("REVIEW001", "리뷰가 존재하지 않습니다.", HttpStatus.NOT_FOUND),
  REVIEW_FORBIDDEN("REVIEW002", "해당 리뷰에 대한 삭제 권한이 없습니다.", HttpStatus.FORBIDDEN),
  INTERNAL_SERVER_ERROR("GLOBAL003", "서버 내부 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);


  private final String code;
  private final String message;
  private final HttpStatus status;
}
