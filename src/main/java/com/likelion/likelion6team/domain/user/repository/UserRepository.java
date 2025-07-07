package com.likelion.likelion6team.domain.user.repository;

import com.likelion.likelion6team.domain.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByEmail(String username);

  boolean existsByEmail(String username);

}
