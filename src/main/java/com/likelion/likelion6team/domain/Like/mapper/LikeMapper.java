package com.likelion.likelion6team.domain.Like.mapper;

import com.likelion.likelion6team.domain.Like.dto.response.LikePostResponse;
import com.likelion.likelion6team.domain.Like.entity.Like;
import com.likelion.likelion6team.domain.post.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LikeMapper {

  public LikePostResponse toResponse(Like like) {
    Post post = like.getPost();

    return LikePostResponse.builder()
        .postId(post.getId())
        .postTitle(post.getTitle())
        .content(post.getContent())
        .build();
  }
}
