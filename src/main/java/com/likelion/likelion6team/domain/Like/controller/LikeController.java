package com.likelion.likelion6team.domain.Like.controller;
import com.likelion.likelion6team.domain.Like.dto.response.LikeCountResponse;
import com.likelion.likelion6team.domain.Like.dto.response.LikePostResponse;
import com.likelion.likelion6team.domain.Like.service.LikeService;
import com.likelion.likelion6team.domain.user.entity.User;
import com.likelion.likelion6team.global.response.BaseResponse;
import com.likelion.likelion6team.global.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/likes")
@RequiredArgsConstructor
@Tag(name = "Likes", description = "좋아요 API")
public class LikeController {

  private final LikeService likeService;

  @PostMapping("/{postId}")
  @Operation(summary = "좋아요 추가")
  public ResponseEntity<BaseResponse<LikeCountResponse>> addLike(
      @PathVariable Long postId,
      @AuthenticationPrincipal CustomUserDetails userDetails) {

    User user = userDetails.getUser();
    LikeCountResponse response = likeService.addLike(user, postId);
    return ResponseEntity.ok(BaseResponse.success("좋아요 추가 성공", response));
  }

  @DeleteMapping("/{postId}")
  @Operation(summary = "좋아요 취소")
  public ResponseEntity<BaseResponse<LikeCountResponse>> cancelLike(
      @PathVariable Long postId,
      @AuthenticationPrincipal CustomUserDetails userDetails) {

    User user = userDetails.getUser();
    LikeCountResponse response = likeService.cancelLike(user, postId);
    return ResponseEntity.ok(BaseResponse.success("좋아요 취소 성공", response));
  }

  @GetMapping("/{postId}")
  @Operation(summary = "좋아요 수 및 상태 조회")
  public ResponseEntity<BaseResponse<LikeCountResponse>> getLikeCount(
      @AuthenticationPrincipal CustomUserDetails userDetails,
      @PathVariable Long postId) {

    User user = userDetails.getUser();
    LikeCountResponse response = likeService.getLikeCount(user, postId);
    return ResponseEntity.ok(BaseResponse.success("좋아요 수 조회 성공", response));
  }

  @GetMapping("/me")
  @Operation(summary = "내가 좋아요한 게시물 목록")
  public ResponseEntity<BaseResponse<List<LikePostResponse>>> getMyLikes(
      @AuthenticationPrincipal CustomUserDetails userDetails) {

    User user = userDetails.getUser();
    List<LikePostResponse> response = likeService.getMyLikes(user);
    return ResponseEntity.ok(BaseResponse.success("좋아요한 게시물 목록 조회 성공", response));
  }
}
