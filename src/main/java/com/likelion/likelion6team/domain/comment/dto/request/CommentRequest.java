package com.likelion.likelion6team.domain.comment.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(title="CommentRequest DTO", description = "댓글 작성을 위한 데이터 전송 ")
public class CommentRequest {

  @NotNull(message = "게시글 항목은 필수입니다.")
  @Schema(description = "게시글 고유 ID", example = "1")
  private Long postId;

  @NotBlank(message = "댓글 내용 항목은 필수입니다.")
  @Schema(description = "댓글 내용", example = "ㅋㅋㅋㅋㅋㅋㅋㅋㅋ")
  private String content;


}
