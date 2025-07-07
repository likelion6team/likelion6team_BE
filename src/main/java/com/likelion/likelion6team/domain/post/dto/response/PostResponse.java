package com.likelion.likelion6team.domain.post.dto.response;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostResponse {
  private Long postId;
  private String title;
  private String content;
}
