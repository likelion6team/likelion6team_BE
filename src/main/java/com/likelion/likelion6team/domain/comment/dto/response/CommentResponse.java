package com.likelion.likelion6team.domain.comment.dto.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(title="CommentResponse DTO", description = "댓글에 대한 데이터 반환")
public class CommentResponse {
  @Schema(description = "후기 고유 ID", example = "1")
  private Long commentId;

  @Schema(description = "작성자 아이디", example = "abc@naver.com")
  private String username;

  @Schema(description = "작성자 이름(별명)", example = "코딩최고")
  private String name;

  @Schema(description = "게시글 고유 ID", example = "1")
  private Long postId;

  @Schema(description = "게시글 제목", example = "오늘 코딩 인증")
  private String postTitle;

  @Schema(description = "후기 내용", example = "맛있게 매워요.")
  private String content;

}
