package com.likelion.likelion6team.domain.Like.repository;

import com.likelion.likelion6team.domain.Like.entity.Like;
import com.likelion.likelion6team.domain.user.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {


  long countByPost(Post post);


  List<Like> findByUser(User user);
}
