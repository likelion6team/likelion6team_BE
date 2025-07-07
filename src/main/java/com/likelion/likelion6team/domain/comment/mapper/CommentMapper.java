package com.likelion.likelion6team.domain.comment.mapper;

import com.likelion.likelion6team.domain.comment.dto.response.CommentResponse;
import com.likelion.likelion6team.domain.comment.entity.Comment;
import com.likelion.likelion6team.global.exception.model.BaseErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;


@Component
public class CommentMapper {


  public CommentResponse toCommentResponse(Comment comment) {
    return CommentResponse.builder()
        .commentId(comment.getId())
        .username(comment.getUser().getUsername())
        .name(comment.getUser().getUsername())
        .postId(comment.getPost().getId())
        .postTitle(comment.getPost().getTitle())
        .content(comment.getContents())
        .build();
  }

}
