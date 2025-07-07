package com.likelion.likelion6team.domain.post.controller;

import com.likelion.likelion6team.domain.auth.exception.AuthErrorCode;
import com.likelion.likelion6team.domain.post.dto.request.PostRequest;
import com.likelion.likelion6team.domain.post.dto.response.PostResponse;
import com.likelion.likelion6team.domain.post.service.PostService;
import com.likelion.likelion6team.global.exception.CustomException;
import com.likelion.likelion6team.global.jwt.JwtProvider;
import com.likelion.likelion6team.global.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name="Post", description="게시글 관련 API")
public class PostController {


  private final PostService postService;
  private final JwtProvider jwtProvider;


  @Operation(summary = "(확인용)게시글 전체 조회",
      description = "게시판 페이지로 이동할 때 요청되는 API.")
  @GetMapping("/posts")
  public ResponseEntity<BaseResponse<List<PostResponse>>> getAllPosts() {
    List<PostResponse> response = postService.getAllPosts();
    return ResponseEntity.ok(BaseResponse.success("전체 게시글 조회 성공", response));
  }

  @Operation(summary = "게시글 단일 조회",
      description = "게시판 페이지에서 특정 게시글에 접근할 때 요청되는 API.")
  @GetMapping("/posts/{postId}")
  public ResponseEntity<BaseResponse<PostResponse>> getPostById(
      @Parameter(description = "특정 게시글 ID")
      @PathVariable Long postId) {
    PostResponse response = postService.getPostById(postId);
    return ResponseEntity.ok(BaseResponse.success("게시글 단일 조회 성공", response));
  }

  @Operation(summary = "게시글 생성",
      description = "게시판 페이지에서 게시글 작성 후 생성 버튼을 눌렀을 때 요청되는 API.")
  @PostMapping("/posts")
  public ResponseEntity<BaseResponse<PostResponse>> createPost(
      @Parameter(description = "게시글 작성 내용")
      @RequestBody @Valid PostRequest postRequest,
      HttpServletRequest request) {
    // 로그인한 아이디 가져오기 //
    String authHeader = request.getHeader("Authorization");
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      throw new CustomException(AuthErrorCode.INVALID_ACCESS_TOKEN);  // 에러 처리 (원하는 예외로 바꿔도 됨)
    }

    String token = authHeader.substring(7).trim();   // "Bearer " 제거
    String userName = jwtProvider.extractUsername(token);

    PostResponse response = postService.createPost(postRequest, userName);
    return ResponseEntity.ok(BaseResponse.success("게시글 생성 성공", response));
  }


  @Operation(summary = "게시글 수정",
      description = "게시판 페이지에서 게시글 수정 후 수정 완료 버튼을 눌렀을 때 요청되는 API.")
  @PutMapping("/posts/{postId}")
  public ResponseEntity<BaseResponse<PostResponse>> updatePost(
      @Parameter(description = "게시글 수정 내용") @RequestBody PostRequest postRequest,
      @Parameter(description = "특정 게시글 ID") @PathVariable Long postId,
      HttpServletRequest request) {
    // 로그인한 아이디 가져오기 //
    String authHeader = request.getHeader("Authorization");
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      throw new CustomException(AuthErrorCode.INVALID_ACCESS_TOKEN);  // 에러 처리 (원하는 예외로 바꿔도 됨)
    }

    String token = authHeader.substring(7).trim();   // "Bearer " 제거
    String userName = jwtProvider.extractUsername(token);

    PostResponse response = postService.updatePost(postId, postRequest, userName);
    return ResponseEntity.ok(BaseResponse.success("게시글 수정 성공", response));
  }

  @Operation(summary = "게시글 삭제",
      description = "게시판 페이지에서 게시글 삭제 버튼을 눌렀을 때 요청되는 API.")
  @DeleteMapping("/posts/{postId}")
  public ResponseEntity<BaseResponse<Boolean>> deletePost(
      @Parameter(description = "특정 게시글 ID") @PathVariable Long postId,
      HttpServletRequest request) {
    // 로그인한 아이디 가져오기 //
    String authHeader = request.getHeader("Authorization");
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      throw new CustomException(AuthErrorCode.INVALID_ACCESS_TOKEN);  // 에러 처리 (원하는 예외로 바꿔도 됨)
    }

    String token = authHeader.substring(7).trim();   // "Bearer " 제거
    String userName = jwtProvider.extractUsername(token);

    Boolean response = postService.deletePost(postId, userName);
    return ResponseEntity.ok(BaseResponse.success("게시글 삭제 성공", response));
  }


//  // 게시글 최신순으로 조회
//  @Operation(summary = "게시글 최신순으로 조회",
//      description = "게시글 생성 시간이 최신인 순으로 조회할 때 요청되는 API.")
//  @GetMapping("/posts/latest")
//  public ResponseEntity<BaseResponse<List<PostResponse>>> getAllPostsLatest() {
//    List<PostResponse> response = postService.getAllPostsLatest();
//    return ResponseEntity.ok(BaseResponse.success("게시글 최신순으로 조회 성공", response));
//  }
//
//
//  // 조회 많은 순으로 조회
//  @Operation(summary = "게시글 조회순으로 조회",
//      description = "게시글 조회수가 높은 순으로 조회할 때 요청되는 API.")
//  @GetMapping("/posts/popular")
//  public ResponseEntity<BaseResponse<List<PostResponse>>> getAllPostsPopular() {
//    List<PostResponse> response = postService.getAllPostsPopular();
//    return ResponseEntity.ok(BaseResponse.success("게시글 조회순으로 조회 성공", response));
//  }


}
