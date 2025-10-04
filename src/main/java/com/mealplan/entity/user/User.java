package com.mealplan.entity.user;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id; // pk

  @Column(name = "login_id", nullable = false, unique = true, length = 10)
  private String loginId; // 회원 id

  @Setter
  @Column(nullable = false, length = 100) 
  private String password; // 회원 비밀번호

  @Setter
  @Column(nullable = false, length = 10)
  private String name; // 회원 이름

  @Setter
  @Column(name = "phone_number", nullable = false, unique = true, length = 11)
  private String phoneNumber; // 회원 연락처

  @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

  @Column(name = "updated_at")
    private LocalDateTime updatedAt;

  @PrePersist
  public void onCreate() {
    this.createdAt = LocalDateTime.now();
    this.updatedAt = LocalDateTime.now();
  }

  @PreUpdate
  public void onUpdate() {
    this.updatedAt = LocalDateTime.now();
  }    
}
