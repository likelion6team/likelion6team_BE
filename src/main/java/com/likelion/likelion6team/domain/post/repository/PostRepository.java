package com.likelion.likelion6team.domain.post.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.likelion.likelion6team.domain.post.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

  // 조회수대로 정렬 
  List<Post> findAllByOrderByViewDesc();

  // 만든 시각 기준 정렬
  List<Post> findAllByOrderByCreatedAtDesc();

}
