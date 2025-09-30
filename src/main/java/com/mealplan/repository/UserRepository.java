package com.mealplan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mealplan.entity.user.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
}
