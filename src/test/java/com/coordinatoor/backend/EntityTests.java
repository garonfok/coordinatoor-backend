package com.coordinatoor.backend;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.coordinatoor.backend.entity.Profile;
import com.coordinatoor.backend.entity.World;
import com.coordinatoor.backend.entity.WorldCoordinate;
import com.coordinatoor.backend.repository.ProfileRepository;
import com.coordinatoor.backend.repository.WorldCoordinateRepository;
import com.coordinatoor.backend.repository.WorldRepository;

@SpringBootTest
@ActiveProfiles("test")
public class EntityTests {

  @Autowired
  private ProfileRepository profileRepository;

  @Autowired
  private WorldRepository worldRepository;

  @Autowired
  private WorldCoordinateRepository worldCoordinateRepository;

  private Profile profile;
  private World world;
  private WorldCoordinate worldCoordinate;

  @BeforeEach
  public void create() {
    profile = profileRepository.save(new Profile("johnsmith", "johnsmith@email.com"));

    world = worldRepository.save(
        new World("Test World", "12345", "12334123", profile));

    worldCoordinate = worldCoordinateRepository.save(
        new WorldCoordinate("Test Coordinate", 1, 2, 3,
            WorldCoordinate.DimensionEnum.OVERWORLD, world));

  }

  @Test
  public void testExists() {
    assertEquals(profile, profileRepository.findById(profile.getId()).get());
    assertEquals(world, worldRepository.findById(world.getId()).get());
    assertEquals(worldCoordinate, worldCoordinateRepository.findById(worldCoordinate.getId()).get());
  }

  @Test
  public void testRoles() {
    world.setOwner(profile);
    worldRepository.save(world);

    assertEquals(profile, world.getOwner());
  }
}
