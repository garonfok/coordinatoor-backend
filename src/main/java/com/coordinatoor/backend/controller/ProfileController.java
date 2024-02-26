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
    return profileRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Could not find profile with specified id"));
  }

  @GetMapping(path = "/email/{email}", produces = "application/json")
  public Profile getProfileByEmail(@PathVariable String email) {
    return profileRepository.findByEmail(email)
        .orElseThrow(() -> new IllegalArgumentException("Could not find profile with specified email"));
  }

  @GetMapping(path = "/sub/{sub}", produces = "application/json")
  public Profile getProfileBySub(@PathVariable String sub) {
    return profileRepository.findByAuth0Sub(sub)
        .orElseThrow(() -> new IllegalArgumentException("Could not find profile with specified sub"));
  }

  @GetMapping(path = "/search/email/{email}", produces = "application/json")
  public List<Profile> getProfilesByEmail(@PathVariable String email) {
    return profileRepository.findByEmailContainsIgnoreCaseOrderByUsername(email);
  }

  @GetMapping(path = "/search/username/{username}", produces = "application/json")
  public List<Profile> getProfilesByUsername(@PathVariable String username) {
    return profileRepository.findByUsernameContainsIgnoreCaseOrderByUsername(username);
  }

  @PostMapping(path = "/", consumes = "application/json", produces = "application/json")
  public Profile createProfile(@RequestBody Profile profile) {
    return profileRepository.save(profile);
  }

  @PutMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
  public Profile updateProfile(@PathVariable Long id, @RequestBody Profile newProfile) {
    Profile profile = profileRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Could not find profile with specified id"));
    profile.setEmail(newProfile.getEmail());
    profile.setUsername(newProfile.getUsername());
    return profileRepository.save(profile);
  }

  @DeleteMapping(path = "/{id}")
  public void deleteProfile(@PathVariable Long id) {
    profileRepository.deleteById(id);
  }
}
