package com.coordinatoor.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coordinatoor.backend.entity.World;
import com.coordinatoor.backend.repository.WorldRepository;

@RestController
@RequestMapping("/world")
public class WorldController {

  @Autowired
  WorldRepository worldRepository;

  @GetMapping("/")
  public List<World> getAllWorlds() {
    return worldRepository.findAll();
  }

  @GetMapping(path = "/{id}", produces = "application/json")
  public World getWorld(Long id) {
    return worldRepository.findById(id).orElse(null);
  }

  @GetMapping(path = "/name/{name}", produces = "application/json")
  public List<World> getWorldByName(String name) {
    return worldRepository.findByNameContainingIgnoreCaseOrderByNameAsc(name);
  }

  @PostMapping(path = "/", consumes = "application/json", produces = "application/json")
  public World createWorld(World world) {
    return worldRepository.save(world);
  }

  @DeleteMapping(path = "/{id}")
  public void deleteWorld(Long id) {
    worldRepository.deleteById(id);
  }

}
