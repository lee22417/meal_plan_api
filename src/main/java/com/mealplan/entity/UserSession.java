package com.mealplan.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_session")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSession {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "us_id")
  private Integer usId; // PK

  @Setter
  @Column(name = "token", nullable = false, length = 255)
  private String token; // jwt token

  @Setter
  @Column(name = "expired_at", nullable = false)
  private LocalDateTime expiredAt; // 만료 시간

  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt; 

  @PrePersist
  public void onCreate() {
    this.createdAt = LocalDateTime.now();
  }

  public static UserSession of(String token, long expirationMs) {
    return UserSession.builder()
      .token(token)
      .expiredAt(LocalDateTime.now().plusNanos(expirationMs * 1_000_000))
      .build();
  }
}
