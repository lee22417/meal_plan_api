package com.mealplan.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mealplan.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
  boolean existsByUserId(String userId);
  boolean existsByUserPhone(String userPhone);
  Optional<User> findByUserId(String userId);
}
