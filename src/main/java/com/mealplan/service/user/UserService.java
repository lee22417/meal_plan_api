package com.mealplan.service.user;

import com.mealplan.entity.User;
import com.mealplan.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
  private final UserRepository userRepository;

  //

  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  public Optional<User> getUserById(int id) {
    return userRepository.findById(id);
  }

  public User saveUser(User user) {
    return userRepository.save(user);
  }
}
