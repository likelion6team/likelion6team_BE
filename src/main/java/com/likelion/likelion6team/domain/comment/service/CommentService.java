package com.likelion.likelion6team.domain.comment.service;


import com.likelion.likelion6team.domain.comment.dto.request.CommentRequest;
import com.likelion.likelion6team.domain.comment.dto.response.CommentResponse;
import com.likelion.likelion6team.domain.comment.entity.Comment;
import com.likelion.likelion6team.domain.comment.exception.CommentErrorCode;
import com.likelion.likelion6team.domain.comment.mapper.CommentMapper;
import com.likelion.likelion6team.domain.comment.repository.CommentRepository;
import com.likelion.likelion6team.domain.post.entity.Post;
import com.likelion.likelion6team.domain.post.exception.PostErrorCode;
import com.likelion.likelion6team.domain.post.repository.PostRepository;
import com.likelion.likelion6team.domain.user.entity.User;
import com.likelion.likelion6team.domain.user.exception.UserErrorCode;
import com.likelion.likelion6team.domain.user.repository.UserRepository;
import com.likelion.likelion6team.global.exception.CustomException;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentService {
  private final CommentRepository commentRepository;
  private final CommentMapper commentMapper;
  private final UserRepository userRepository;
  private final PostRepository postRepository;



  // (확인용)댓글 Id로, 댓글 단일 조회
  @Transactional
  public CommentResponse getReview(Long reviewId) {
    // 넘겨받은 reviewId와 같은 id 찾기. 없으면 에러
    Comment comment = commentRepository.findById(reviewId).orElseThrow(() -> new CustomException(
        CommentErrorCode.COMMENT_NOT_FOUND));

    // 해당 게시글 정보를 리턴.
    return commentMapper.toCommentResponse(comment);
  }


  // (확인용)댓글 리스트 전체 조회
  @Transactional
  public List<CommentResponse> getAllReviews() {
    List<Comment> commentList = commentRepository.findAll();

    return commentList.stream().map(commentMapper::toCommentResponse).toList();
  }



  // 게시글 별 댓글 리스트 전체 조회
  @Transactional
  public List<CommentResponse> getAllReviewsByFoodId(Long foodId) {

    Post post = postRepository.findById(foodId).orElseThrow(() -> new CustomException(PostErrorCode.POST_ERROR_FOUND));
    List<Comment> commentList = commentRepository.findByPost(post);

    return commentList.stream().map(commentMapper::toCommentResponse).toList();
  }


  // 회원 별 댓글 리스트 전체 조회
  @Transactional
  public List<CommentResponse> getAllReviewsByUserName(String userName) {

    User user = userRepository.findByEmail(userName).orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));
    List<Comment> commentList = commentRepository.findByUser(user);


    return commentList.stream().map(commentMapper::toCommentResponse).toList();
  }




  // 댓글 작성
  @Transactional
  public CommentResponse createReview(String userName, CommentRequest request) {

    // 1. 로그인 된 아이디로 회원이 있는지 확인. 없으면 에러
    User user = userRepository.findByEmail(userName)
        .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

    // 2. 게시글 id로 해당 게시글 있는지 확인
    Post post = postRepository.findById(request.getPostId())
        .orElseThrow(() -> new CustomException(PostErrorCode.POST_ERROR_FOUND));



    // 4. comment 객체를 만듦.
    Comment comment = Comment.builder()
        .contents(request.getContent())
        .user(user)
        .post(post)
        .build();

    // 5. Review 엔티티로 레포지토리에 접근(저장)
    Comment savedReview = commentRepository.save(comment);
    log.info("New review created: {}", savedReview.getId());

    return commentMapper.toCommentResponse(savedReview);
  }


  // 댓글 수정
  @Transactional
  public CommentResponse updateReview(Long reviewId, String userName, CommentRequest request) {
    // 1. 댓글 찾기
    Comment comment = commentRepository.findById(reviewId)
        .orElseThrow(() -> new CustomException(CommentErrorCode.COMMENT_NOT_FOUND));

    // 2. 로그인한 사용자와 댓글 작성자가 일치하는지 확인
    if (!comment.getUser().getEmail().equals(userName)) {
      throw new CustomException(CommentErrorCode.NO_PERMISSION_TO_UPDATE);
    }

    // 3. 게시글 정보 가져오기
    Post post = comment.getPost();

    // 요청으로 들어온 음식 ID와 실제 댓글 가리키는 음식 ID가 다르면 에러
    if (!post.getId().equals(request.getPostId())) {
      throw new CustomException(CommentErrorCode.INVALID_FOOD_ID_FOR_COMMENT_UPDATE);
    }

    // 4. 후기 평점, 후기 내용만 수정
    comment.setContents(request.getContent());


    // 6. Review 엔티티로 레포지토리에 접근(수정)
    return commentMapper.toCommentResponse(comment);

  }


  // 댓글 삭제
  @Transactional
  public boolean deleteReview(String userName, Long id) {
    log.info("[서비스] 댓글 삭제 시도: id= {}", id);
    // 1. 로그인 된 아이디로 회원이 있는지 확인. 없으면 에러
    User user = userRepository.findByEmail(userName)
        .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));


    // 2. 댓글 id로 해당 후기가 존재하는지 체크
    Comment comment = commentRepository.findById(id)
        .orElseThrow(() -> {
          log.warn("[서비스] 댓글 삭제 실패 - 존재하지 않음: id= {}", id);
          return new CustomException(CommentErrorCode.COMMENT_NOT_FOUND);
        });

    // 3. 해당 댓글 삭제
    commentRepository.deleteById(id);
    log.info("[서비스] 댓글 삭제 완료: id= {}", id);


    return true;
  }




}
