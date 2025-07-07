package com.likelion.likelion6team.domain.Like.repository;

import com.likelion.likelion6team.domain.Like.entity.Like;
import com.likelion.likelion6team.domain.post.entity.Post;
import com.likelion.likelion6team.domain.user.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {

  boolean existsByUserAndPost(User user, Post post);

  Optional<Like> findByUserAndPost(User user, Post post);

  long countByPost(Post post);

  List<Like> findByUser(User user);
}
