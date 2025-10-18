package com.mealplan.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mealplan.entity.UserSession;

@Repository
public interface UserSessionRepository extends JpaRepository<UserSession, Integer> {

}
