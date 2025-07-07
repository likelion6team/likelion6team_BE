package com.likelion.likelion6team.domain.Like.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "좋아요한 음식 응답 DTO")
public class LikePostResponse {

  @Schema(description = "게시물 ID")
  private Long postId;

  @Schema(description = "게시글 이름")
  private String postTitle;

  @Schema(description = "게시글 내용")
  private String content;
}
