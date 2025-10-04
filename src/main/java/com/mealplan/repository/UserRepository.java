package com.mealplan.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mealplan.entity.user.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
  boolean existsByLoginId(String loginId);
  boolean existsByPhoneNumber(String phoneNumber);
  Optional<User> findByLoginId(String loginId);
}
