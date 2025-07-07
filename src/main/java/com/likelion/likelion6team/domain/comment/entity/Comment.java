package com.likelion.likelion6team.domain.comment.entity;


import com.likelion.likelion6team.domain.post.entity.Post;
import com.likelion.likelion6team.domain.user.entity.User;
import com.likelion.likelion6team.global.common.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name="comment")
public class Comment extends BaseTimeEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "contents", nullable = false)
  private String contents;

  // 회원 (외래키 - User 엔티티)
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user", nullable = false) // 외래키 컬럼 이름
  private User user;

  // 게시글 (외래키 - Post 엔티티)
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "post", nullable = false)
  private Post post;


}
