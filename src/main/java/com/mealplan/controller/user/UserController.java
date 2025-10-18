package com.mealplan.controller.user;

import com.mealplan.entity.User;
import com.mealplan.service.user.UserService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

  @Autowired private UserService userService;

  @GetMapping
  public List<User> getAllUsers() {
    return userService.getAllUsers();
  }

  @GetMapping("/{id}")
  public ResponseEntity<User> getUserById(@PathVariable int id) {
    Optional<User> user = userService.getUserById(id);
    return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping
  public User createUser(@RequestBody User user) {
    return userService.saveUser(user);
  }

  @PutMapping("/{id}")
  public ResponseEntity<User> updateUser(@PathVariable int id, @RequestBody User userDetails) {
    Optional<User> optionalUser = userService.getUserById(id);
    if (optionalUser.isPresent()) {
      User user = optionalUser.get();
      // user.setName(userDetails.getName());
      // user.setEmail(userDetails.getEmail());
      User updatedUser = userService.saveUser(user);
      return ResponseEntity.ok(updatedUser);
    }
    return null;
  }
}
