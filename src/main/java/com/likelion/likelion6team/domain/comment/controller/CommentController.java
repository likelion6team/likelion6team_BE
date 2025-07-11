package com.likelion.likelion6team.domain.comment.controller;

import com.likelion.likelion6team.domain.auth.exception.AuthErrorCode;
import com.likelion.likelion6team.domain.comment.dto.request.CommentRequest;
import com.likelion.likelion6team.domain.comment.dto.response.CommentResponse;
import com.likelion.likelion6team.domain.comment.service.CommentService;
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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
@Tag(name="Comment", description="Comment 관리 API")
public class CommentController {

  private final CommentService commentService;
  private final JwtProvider jwtProvider;


  // 후기 작성
  @Operation(summary="후기 작성 API", description ="후기 작성을 위한 API")
  @PostMapping("")
  public ResponseEntity<BaseResponse<CommentResponse>> createReview(
      @RequestBody @Valid CommentRequest reviewRequest,
      HttpServletRequest request) {
    // 로그인한 아이디 가져오기 //
    String authHeader = request.getHeader("Authorization");
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      throw new CustomException(AuthErrorCode.INVALID_ACCESS_TOKEN);  // 에러 처리 (원하는 예외로 바꿔도 됨)
    }

    String token = authHeader.substring(7).trim();   // "Bearer " 제거
    String userName = jwtProvider.extractUsername(token);  // 토큰에서 로그인한 아이디 추출


    CommentResponse reviewResponse = commentService.createReview(userName, reviewRequest);
    return ResponseEntity.ok(BaseResponse.success("후기 작성에 성공했습니다.", reviewResponse));
  }


  // 후기 수정
  @Operation(summary="후기 수정 API", description ="후기 수정을 위한 API")
  @PatchMapping("/{reviewId}")
  public ResponseEntity<BaseResponse<CommentResponse>> updateReview(
      @PathVariable Long reviewId,
      @RequestBody @Valid CommentRequest reviewRequest,
      HttpServletRequest request) {
    // 로그인한 아이디 가져오기 //
    String authHeader = request.getHeader("Authorization");
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      throw new CustomException(AuthErrorCode.INVALID_ACCESS_TOKEN);  // 에러 처리 (원하는 예외로 바꿔도 됨)
    }

    String token = authHeader.substring(7).trim();   // "Bearer " 제거
    String userName = jwtProvider.extractUsername(token);  // 토큰에서 로그인한 아이디 추출


    CommentResponse reviewResponse = commentService.updateReview(reviewId, userName, reviewRequest);
    return ResponseEntity.ok(BaseResponse.success("후기 수정에 성공했습니다.", reviewResponse));
  }

  // 후기 삭제
  @Operation(summary="후기 삭제 API", description ="후기 삭제를 위한 API")
  @DeleteMapping("/{id}")
  public ResponseEntity<BaseResponse<Boolean>> deleteReview(
      @Parameter(description = "특정 후기 ID") @PathVariable Long id, HttpServletRequest request ) {
    // 로그인한 아이디 가져오기 //
    String authHeader = request.getHeader("Authorization");
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      throw new CustomException(AuthErrorCode.INVALID_ACCESS_TOKEN);  // 에러 처리 (원하는 예외로 바꿔도 됨)
    }

    String token = authHeader.substring(7).trim();   // "Bearer " 제거
    String userName = jwtProvider.extractUsername(token);  // 토큰에서 로그인한 아이디 추출

    Boolean response = commentService.deleteReview(userName, id);
    return ResponseEntity.ok(BaseResponse.success("후기 삭제에 성공했습니다.", response));
  }




  // 상세 음식 페이지 -> 음식별 후기 리스트 조회
  @Operation(summary = "음식별 후기 리스트 조회",
      description = "음식별 후기 리스트 조회하는 API.")
  @GetMapping("/food/{foodId}")
  public ResponseEntity<BaseResponse<List<CommentResponse>>> getAllReviewsByFoodId(
      @Parameter(description = "특정 음식 ID", example = "1")
      @PathVariable Long foodId ) {
    List<CommentResponse> response = commentService.getAllReviewsByFoodId(foodId);
    return ResponseEntity.ok(BaseResponse.success("음식별 후기 리스트 조회 성공", response));
  }



  // 마이페이지 -> 회원별 후기 리스트 조회
  @Operation(summary = "회원별 후기 리스트 조회",
      description = "회원별 후기 리스트 조회하는 API.")
  @GetMapping("/me")
  public ResponseEntity<BaseResponse<List<CommentResponse>>> getMyReviews(
      HttpServletRequest request) {
    // 로그인한 아이디 가져오기 //
    String authHeader = request.getHeader("Authorization");
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      throw new CustomException(AuthErrorCode.INVALID_ACCESS_TOKEN);  // 에러 처리 (원하는 예외로 바꿔도 됨)
    }

    String token = authHeader.substring(7).trim();   // "Bearer " 제거
    String userName = jwtProvider.extractUsername(token);  // 토큰에서 로그인한 아이디 추출


    List<CommentResponse> response = commentService.getAllReviewsByUserName(userName);
    return ResponseEntity.ok(BaseResponse.success("회원별 후기 리스트 조회 성공", response));
  }


  // (확인용) 단일 후기 조회
  @Operation(summary = "후기 단일 조회",
      description = "단일 후기 정보를 조회하는 API.")
  @GetMapping("/review/{reviewId}")
  public ResponseEntity<BaseResponse<CommentResponse>> getReviewById(
      @Parameter(description = "후기 고유 ID", example = "1")
      @PathVariable Long reviewId) {
    CommentResponse response = commentService.getReview(reviewId);
    return ResponseEntity.ok(BaseResponse.success("단일 후기 조회 성공", response));
  }


  // (확인용) 전체 후기 리스트 조회
  @Operation(summary = "후기 전체 조회",
      description = "전체 후기 정보 리스트를 조회하는 API.")
  @GetMapping("")
  public ResponseEntity<BaseResponse<List<CommentResponse>>> getAllReviews() {
    List<CommentResponse> response = commentService.getAllReviews();
    return ResponseEntity.ok(BaseResponse.success("전체 후기 조회 성공", response));
  }






}
