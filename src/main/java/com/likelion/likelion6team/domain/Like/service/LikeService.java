package com.likelion.likelion6team.domain.Like.service;

import static com.likelion.likelion6team.global.exception.GlobalErrorCode.ALREADY_LIKED;
import static com.likelion.likelion6team.global.exception.GlobalErrorCode.NOT_FOUND_LIKE;
import static com.likelion.likelion6team.global.exception.GlobalErrorCode.NOT_FOUND_POST;

import com.likelion.likelion6team.domain.Like.dto.response.LikeCountResponse;
import com.likelion.likelion6team.domain.Like.dto.response.LikePostResponse;
import com.likelion.likelion6team.domain.Like.entity.Like;
import com.likelion.likelion6team.domain.Like.mapper.LikeMapper;
import com.likelion.likelion6team.domain.Like.repository.LikeRepository;
import com.likelion.likelion6team.domain.post.entity.Post;
import com.likelion.likelion6team.domain.post.repository.PostRepository;
import com.likelion.likelion6team.domain.user.entity.User;
import com.likelion.likelion6team.global.exception.CustomException;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {

  private final LikeRepository likeRepository;
  private final PostRepository postRepository;
  private final LikeMapper likeMapper;

  @Transactional
  public LikeCountResponse addLike(User user, Long postId) {
    Post post = postRepository.findById(postId)
        .orElseThrow(() -> new CustomException(NOT_FOUND_POST));

    if (likeRepository.existsByUserAndPost(user, post)) {
      throw new CustomException(ALREADY_LIKED);
    }

    likeRepository.save(Like.builder()
        .user(user)
        .post(post)
        .build());

    long count = likeRepository.countByPost(post);
    return new LikeCountResponse(postId, count, true);
  }

  @Transactional
  public LikeCountResponse cancelLike(User user, Long postId) {
    Post post = postRepository.findById(postId)
        .orElseThrow(() -> new CustomException(NOT_FOUND_POST));

    Like like = likeRepository.findByUserAndPost(user, post)
        .orElseThrow(() -> new CustomException(NOT_FOUND_LIKE));

    likeRepository.delete(like);

    long count = likeRepository.countByPost(post);
    return new LikeCountResponse(postId, count, false);
  }

  public LikeCountResponse getLikeCount(User user, Long postId) {
    Post post = postRepository.findById(postId)
        .orElseThrow(() -> new CustomException(NOT_FOUND_POST));

    long count = likeRepository.countByPost(post);
    boolean liked = likeRepository.existsByUserAndPost(user, post);
    return new LikeCountResponse(postId, count, liked);
  }

  public List<LikePostResponse> getMyLikes(User user) {
    return likeRepository.findByUser(user).stream()
        .map(likeMapper::toResponse)
        .collect(Collectors.toList());
  }
}
