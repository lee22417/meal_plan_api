package com.mealplan.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mealplan.entity.UserSession;

@Repository
public interface UserSessionRepository extends JpaRepository<UserSession, Integer> {
  Optional<UserSession> findByToken(String token);
}
