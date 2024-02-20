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
import com.coordinatoor.backend.entity.World;
import com.coordinatoor.backend.entity.WorldProfile.Role;
import com.coordinatoor.backend.repository.ProfileRepository;
import com.coordinatoor.backend.repository.WorldRepository;

@RestController
@RequestMapping("/world")
public class WorldController {

  @Autowired
  WorldRepository worldRepository;

  @Autowired
  ProfileRepository profileRepository;

  @GetMapping(path = "/{id}", produces = "application/json")
  public World getWorld(@PathVariable Long id) {
    return worldRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Could not find world with specified id"));
  }

  @GetMapping(path = "/search/name/{name}", produces = "application/json")
  public List<World> getWorldByName(@PathVariable String name) {
    return worldRepository.findByNameContainsIgnoreCase(name);
  }

  @GetMapping(path = "/user/{id}/owner", produces = "application/json")
  public Profile getWorldOwnerProfile(@PathVariable Long id) {
    List<Profile> owners = profileRepository.findByWorlds_WorldIdAndWorlds_RoleOrderByUsername(id, Role.OWNER);
    return owners.isEmpty() ? null
        : owners.get(0);
  }

  @PostMapping(path = "/", consumes = "application/json", produces = "application/json")
  public World createWorld(@RequestBody World world) {
    return worldRepository.save(world);
  }

  @PutMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
  public World updateWorld(@PathVariable Long id, @RequestBody World newWorld) {
    World world = worldRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Could not find world with specified id"));
    world.setName(newWorld.getName());
    world.setSeed(newWorld.getSeed());
    world.setIpAddress(newWorld.getIpAddress());
    return worldRepository.save(world);
  }

  @DeleteMapping(path = "/{id}")
  public void deleteWorld(@PathVariable Long id) {
    worldRepository.deleteById(id);
  }

}
