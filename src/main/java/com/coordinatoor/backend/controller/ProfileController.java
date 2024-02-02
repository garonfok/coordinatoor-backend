package com.coordinatoor.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coordinatoor.backend.entity.Profile;
import com.coordinatoor.backend.repository.ProfileRepository;

@RestController
@RequestMapping("/profile")
public class ProfileController {

  @Autowired
  ProfileRepository profileRepository;

  @GetMapping(path = "/{id}", produces = "application/json")
  public Profile getUser(@PathVariable("id") Long id) {
    return profileRepository.findById(id).orElse(null);
  }

  @GetMapping(path = "/email/{email}", produces = "application/json")
  public Profile getProfileByEmail(@PathVariable("email") String email) {
    return profileRepository.findByEmail(email).orElse(null);
  }

  @GetMapping(path = "/search/email/{email}", produces = "application/json")
  public List<Profile> getUserByEmail(@PathVariable("email") String email) {
    return profileRepository.findByEmailContains(email);
  }

  @GetMapping(path = "/search/username/{username}", produces = "application/json")
  public List<Profile> getUserByUsername(@PathVariable("username") String username) {
    return profileRepository.findByUsernameContains(username);
  }

  @PostMapping(path = "/", consumes = "application/json", produces = "application/json")
  public Profile createUser(Profile user) {
    return profileRepository.save(user);
  }

  @DeleteMapping(path = "/{id}")
  public void deleteUser(@PathVariable("id") Long id) {
    profileRepository.deleteById(id);
  }
}
