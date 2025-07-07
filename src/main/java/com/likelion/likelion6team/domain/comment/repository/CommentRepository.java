package com.likelion.likelion6team.domain.comment.repository;

import com.likelion.likelion6team.domain.comment.entity.Comment;
import com.likelion.likelion6team.domain.post.entity.Post;
import com.likelion.likelion6team.domain.user.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface CommentRepository  extends JpaRepository<Comment, Long> {
  List<Comment> findByUser(User user);
  List<Comment> findByPost(Post post);
  Optional<Comment> findByUserAndPost(User user, Post post);
  Long countByUser(User user);
  boolean existsByUserAndPost(User user, Post post);

  // 1. 후기 개수
  @Query("SELECT COUNT(r) FROM Comment r WHERE r.post.id = :postId")
  Long countByPostId(@Param("postId") Long postId);


}
