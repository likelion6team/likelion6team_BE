package com.likelion.likelion6team.domain.Like.mapper;

import com.likelion.likelion6team.domain.Like.dto.response.LikePostResponse;
import com.likelion.likelion6team.domain.Like.entity.Like;
import com.likelion.likelion6team.domain.Post.entity.Post;
import com.likelion.likelion6team.domain.Review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LikeMapper {

  private final ReviewRepository reviewRepository;

  public LikePostResponse toResponse(Like like) {
    Post post = like.getPost(); // Like → Post 연관관계 필요

    return LikePostResponse.builder()
        .postId(post.getId())
        .postName(post.getName())  // post.getTitle()로 수정할 수도 있음
        .introduction(post.getIntroduction())  // post에 해당 필드 존재 필요
        .build();
  }
}