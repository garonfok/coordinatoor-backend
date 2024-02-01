package com.coordinatoor.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coordinatoor.backend.model.Profile;
import com.coordinatoor.backend.repository.ProfileRepository;

@RestController
@RequestMapping("/user")
public class UserController {

  @Autowired
  ProfileRepository userRepository;

  @GetMapping("/")
  public List<Profile> getAllUsers() {
    return userRepository.findAll();
  }

  @GetMapping(path = "/{id}", produces = "application/json")
  public Profile getUser(@PathVariable("id") Long id) {
    return userRepository.findById(id).orElse(null);
  }

  @GetMapping(path = "/email/{email}", produces = "application/json")
  public Profile getUserByEmail(@PathVariable("email") String email) {
    return userRepository.findByEmail(email).orElse(null);
  }

  @GetMapping(path = "/username/{username}", produces = "application/json")
  public Profile getUserByUsername(@PathVariable("username") String username) {
    return userRepository.findByUsername(username).orElse(null);
  }

  @PostMapping(path = "/", consumes = "application/json", produces = "application/json")
  public Profile createUser(Profile user) {
    return userRepository.save(user);
  }

  @DeleteMapping(path = "/{id}")
  public void deleteUser(@PathVariable("id") Long id) {
    userRepository.deleteById(id);
  }
}
