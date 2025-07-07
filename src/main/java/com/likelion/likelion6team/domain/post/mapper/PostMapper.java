package com.likelion.likelion6team.domain.post.mapper;

import com.likelion.likelion6team.domain.post.dto.response.PostResponse;
import com.likelion.likelion6team.domain.post.entity.Post;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {



  public PostResponse toPostResponse(Post post) {
    return PostResponse.builder()
        .postId(post.getId())
        .title(post.getTitle())
        .content(post.getContent())
        .build();
  }

}
