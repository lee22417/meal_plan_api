package com.mealplan.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mealplan.entity.User;

@Repository
public interface UserSesssionRepository extends JpaRepository<User, Integer> {

}
