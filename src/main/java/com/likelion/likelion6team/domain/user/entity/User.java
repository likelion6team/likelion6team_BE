package com.likelion.likelion6team.domain.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.likelion.likelion6team.global.common.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(name = "email", nullable = false, unique = true)
  private String email;

  @Column(name = "username", nullable = false)
  private String username;

  @JsonIgnore
  @Column(name = "password", nullable = false)
  private String password;
  @JsonIgnore
  @Column(name = "refresh_token")
  private String refreshToken;
  @Column
  @Enumerated
  @Builder.Default
  private Role role = Role.USER;
  //  마이페이지에 소개랑 국가
  public void createRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
  }
}
