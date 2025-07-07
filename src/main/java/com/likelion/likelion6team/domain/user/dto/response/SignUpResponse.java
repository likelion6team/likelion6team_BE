package com.likelion.likelion6team.domain.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
@Schema(title = "SignUpResponse DTO", description = "사용자 회원가입에 대한 응답 변환")

public class SignUpResponse {

  @Schema(description = "회원가입된 사용자 ID", example = "1")
  private Long userId;
  @Schema(description = "회원가입된 사용자 이메일", example = "seoyeon@gmail.com")
  private String email;
  @Schema(description = "회원가입된 사용자 이름", example = "누구")
  private String username;


}
