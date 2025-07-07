package com.likelion.likelion6team.domain.post.service;


import com.likelion.likelion6team.domain.post.dto.request.PostRequest;
import com.likelion.likelion6team.domain.post.dto.response.PostResponse;
import com.likelion.likelion6team.domain.post.entity.Post;
import com.likelion.likelion6team.domain.post.exception.PostErrorCode;
import com.likelion.likelion6team.domain.post.mapper.PostMapper;
import com.likelion.likelion6team.domain.post.repository.PostRepository;
import com.likelion.likelion6team.domain.user.entity.User;
import com.likelion.likelion6team.domain.user.exception.UserErrorCode;
import com.likelion.likelion6team.domain.user.repository.UserRepository;
import com.likelion.likelion6team.global.exception.CustomException;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {
  private final PostRepository postRepository;
  private final PostMapper postMapper;

  private final UserRepository userRepository;



  // 게시글 전체 조회
  public List<PostResponse> getAllPosts() {
    log.info("[서비스] 게시글 전체 조회 시도");
    List<Post> postList = postRepository.findAll();
    log.info("[서비스] 조회된 게시글 수: {}", postList.size());
    return postList.stream().map(postMapper::toPostResponse).toList();
  }

  // 게시글 생성
  @Transactional
  public PostResponse createPost(PostRequest createPostRequest, String username) {
    log.info("[서비스] 게시글 생성 시도: title= {}, content= {}", createPostRequest.getTitle(), createPostRequest.getContent());


    User user = userRepository.findByEmail(username)
        .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));


    if(createPostRequest.getTitle() == null || createPostRequest.getTitle().isBlank()) {
      throw new CustomException(PostErrorCode.INVALID_POST_TITLE);
    }

    if(createPostRequest.getContent() == null || createPostRequest.getContent().isBlank()) {
      throw new CustomException(PostErrorCode.INVALID_POST_CONTENT);
    }

    if(createPostRequest.getTitle().length() > 10) {
      throw new CustomException(PostErrorCode.TITLE_TOO_LONG);
    }

    Post post = Post.builder()
        .title(createPostRequest.getTitle())
        .content(createPostRequest.getContent())
        .view(0L)
        .user(user)
        .build();
    postRepository.save(post);
    log.info("[서비스] 게시글 생성 완료: id={}, title= {}, content= {}", post.getId(), post.getTitle(), post.getContent());
    return postMapper.toPostResponse(post);
  }


  // 게시글 단일 조회
  public PostResponse getPostById(Long id) {
    log.info("[서비스] 게시글 단일 조회 시도: id={}", id);
    Post post = postRepository.findById(id)

        .orElseThrow(() -> {
          log.warn("[서비스] 게시글 조회 실패 - 존재하지 않음: id={}", id);
          return new CustomException(PostErrorCode.POST_ERROR_FOUND);
        });
    log.info("[서비스] 게시글 단일 조회 성공: id={}", id);
    return postMapper.toPostResponse(post);
  }



  // 게시글 수정
  @Transactional
  public PostResponse updatePost(Long id, PostRequest updatePostRequest, String username) {
    log.info("[서비스] 게시글 수정 시도: id= {}, newTitle= {}, newContent= {}", id, updatePostRequest.getTitle(), updatePostRequest.getContent());


    Post post = postRepository.findById(id)
        .orElseThrow(() -> new CustomException(PostErrorCode.POST_ERROR_FOUND));


    if (!post.getUser().getEmail().equals(username)) {
      throw new CustomException(PostErrorCode.NO_PERMISSION_TO_UPDATE);
    }


    if(updatePostRequest.getTitle() == null || updatePostRequest.getTitle().isBlank()) {
      throw new CustomException(PostErrorCode.INVALID_POST_TITLE);
    }

    if(updatePostRequest.getContent() == null || updatePostRequest.getContent().isBlank()) {
      throw new CustomException(PostErrorCode.INVALID_POST_CONTENT);
    }

    if(updatePostRequest.getTitle().length() > 10) {
      throw new CustomException(PostErrorCode.TITLE_TOO_LONG);
    }


    post.update(updatePostRequest.getTitle(), updatePostRequest.getContent());

    log.info("[서비스] 게시글 수정 완료: id= {}, title= {}, content= {}", post.getId(), post.getTitle(), post.getContent());
    return postMapper.toPostResponse(post);


  }


  // 게시글 삭제
  @Transactional
  public boolean deletePost(Long id, String username) {


    log.info("[서비스] 게시글 삭제 시도: id= {}", id);


    Post post = postRepository.findById(id)
        .orElseThrow(() -> new CustomException(PostErrorCode.POST_ERROR_FOUND));


    if (!post.getUser().getEmail().equals(username)) {
      throw new CustomException(PostErrorCode.NO_PERMISSION_TO_UPDATE);
    }


    postRepository.deleteById(id);
    log.info("[서비스] 게시글 삭제 완료: id= {}", id);
    return true;
  }

//
//  // 전체 게시글 최신순 조회
//  public List<PostResponse> getAllPostsLatest(){
//
//    List<Post> postList = (List<Post>) postRepository.findAllByOrderByCreatedAtDesc();
//    return postList.stream().map(postMapper::toPostResponse).toList();
//  }
//
//  // 전체 게시글 조회 많은 순 조회
//  public List<PostResponse> getAllPostsPopular(){
//    List<Post> postList = (List<Post>) postRepository.findAllByOrderByViewDesc();
//    return postList.stream().map(postMapper::toPostResponse).toList();
//  }



}
