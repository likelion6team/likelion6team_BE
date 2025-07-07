package com.likelion.likelion6team.domain.post.entity;

import com.likelion.likelion6team.domain.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jdk.jfr.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.likelion.likelion6team.global.common.BaseTimeEntity;


@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "post")
public class Post extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String title; // 제목

  @Column(nullable = false)
  private String content; // 내용

  @Column(nullable = false)
  private Long view;

  @Column(nullable = false)
  private String content;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user", nullable = false) // 외래키 컬럼 이름
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "category", nullable = false) // 외래키 컬럼 이름
  private Category category;


  public void update(String title, String content) {
    this.title = title;
    this.content = content;
  }

}
