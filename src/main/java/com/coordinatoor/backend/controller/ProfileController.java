package com.coordinatoor.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
  public Profile getProfile(@PathVariable Long id) {
    return profileRepository.findById(id).orElse(null);
  }

  @GetMapping(path = "/email/{email}", produces = "application/json")
  public Profile getProfileByEmail(@PathVariable String email) {
    return profileRepository.findByEmail(email).orElse(null);
  }

  @GetMapping(path = "/search/email/{email}", produces = "application/json")
  public List<Profile> getProfilesByEmail(@PathVariable String email) {
    return profileRepository.findByEmailContains(email);
  }

  @GetMapping(path = "/search/username/{username}", produces = "application/json")
  public List<Profile> getProfilesByUsername(@PathVariable String username) {
    return profileRepository.findByUsernameContains(username);
  }

  @PostMapping(path = "/", consumes = "application/json", produces = "application/json")
  public Profile createProfile(@RequestBody Profile profile) {
    return profileRepository.save(profile);
  }

  @PutMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
  public Profile updateProfile(@PathVariable Long id, @RequestBody Profile newProfile) {
    return profileRepository.findById(id).map(
        profile -> {
          profile.setEmail(newProfile.getEmail());
          profile.setUsername(newProfile.getUsername());
          return profileRepository.save(profile);
        }).orElseGet(() -> {
          newProfile.setId(id);
          return profileRepository.save(newProfile);
        });
  }

  @DeleteMapping(path = "/{id}")
  public void deleteProfile(@PathVariable Long id) {
    profileRepository.deleteById(id);
  }
}
