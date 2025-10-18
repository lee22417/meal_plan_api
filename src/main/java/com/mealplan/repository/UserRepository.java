package com.mealplan.repository;

import com.mealplan.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
  boolean existsByUserId(String userId);

  boolean existsByUserPhone(String userPhone);

  Optional<User> findByUserId(String userId);
}
