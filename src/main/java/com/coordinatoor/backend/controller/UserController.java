package com.coordinatoor.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coordinatoor.backend.model.User;
import com.coordinatoor.backend.repository.UserRepository;

@RestController
@RequestMapping("/user")
public class UserController {

  @Autowired
  UserRepository userRepository;

  @GetMapping("/")
  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  @GetMapping(path = "/{id}", produces = "application/json")
  public User getUser(@PathVariable("id") Long id) {
    return userRepository.findById(id).orElse(null);
  }

  @GetMapping(path = "/email/{email}", produces = "application/json")
  public User getUserByEmail(@PathVariable("email") String email) {
    return userRepository.findByEmail(email).orElse(null);
  }

  @GetMapping(path = "/username/{username}", produces = "application/json")
  public User getUserByUsername(@PathVariable("username") String username) {
    return userRepository.findByUsername(username).orElse(null);
  }

  @PostMapping(path = "/", consumes = "application/json", produces = "application/json")
  public User createUser(User user) {
    return userRepository.save(user);
  }

  @DeleteMapping(path = "/{id}")
  public void deleteUser(@PathVariable("id") Long id) {
    userRepository.deleteById(id);
  }
}
