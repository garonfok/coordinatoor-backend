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

import com.coordinatoor.backend.entity.World;
import com.coordinatoor.backend.entity.WorldCoordinate;
import com.coordinatoor.backend.repository.WorldCoordinateRepository;
import com.coordinatoor.backend.repository.WorldRepository;

@RestController
@RequestMapping("/world-coordinate")
public class WorldCoordinateController {

  @Autowired
  private WorldCoordinateRepository worldCoordinateRepository;

  @Autowired
  private WorldRepository worldRepository;

  @GetMapping(path = "/{id}", produces = "application/json")
  public WorldCoordinate getWorldCoordinate(@PathVariable("id") Long id) {
    return worldCoordinateRepository.findById(id).orElse(null);
  }

  @GetMapping(path = "/world/{id}", produces = "application/json")
  public List<WorldCoordinate> getWorldCoordinatesByWorld(@PathVariable("id") Long id) {
    World world = worldRepository.findById(id).orElseThrow(
        () -> new IllegalArgumentException("World not found"));
    return worldCoordinateRepository.findByWorldOrderByName(world);
  }

  @PostMapping(path = "/", consumes = "application/json", produces = "application/json")
  public WorldCoordinate createWorldCoordinate(WorldCoordinate worldCoordinate) {
    return worldCoordinateRepository.save(worldCoordinate);
  }

  @PutMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
  public WorldCoordinate updateWorldCoordinate(@PathVariable("id") Long id,
      @RequestBody WorldCoordinate newWorldCoordinate) {
    return worldCoordinateRepository.findById(id).map(
        worldCoordinate -> {
          worldCoordinate.setName(newWorldCoordinate.getName());
          worldCoordinate.setX(newWorldCoordinate.getX());
          worldCoordinate.setY(newWorldCoordinate.getY());
          worldCoordinate.setZ(newWorldCoordinate.getZ());
          worldCoordinate.setDimension(newWorldCoordinate.getDimension());
          return worldCoordinateRepository.save(worldCoordinate);
        }).orElseGet(() -> {
          newWorldCoordinate.setId(id);
          return worldCoordinateRepository.save(newWorldCoordinate);
        });
  }

  @DeleteMapping(path = "/{id}")
  public void deleteWorldCoordinate(@PathVariable("id") Long id) {
    worldCoordinateRepository.deleteById(id);
  }
}
