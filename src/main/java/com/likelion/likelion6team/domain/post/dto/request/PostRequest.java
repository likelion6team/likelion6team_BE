package com.likelion.likelion6team.domain.post.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class PostRequest {

  @NotBlank
  private String title;
  @NotBlank
  private String content;

  @NotBlank
  private Category category;


}
