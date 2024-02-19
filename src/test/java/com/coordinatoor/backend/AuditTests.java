package com.coordinatoor.backend;

import static org.junit.jupiter.api.Assertions.assertNotNull;

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
public class AuditTests {

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
  public void testSave() {
    profile = profileRepository.save(
        new Profile("John Smith", "johnsmith@email.com"));

    world = worldRepository.save(
        new World("Test World", "12345", "12334123"));

    worldCoordinate = worldCoordinateRepository.save(
        new WorldCoordinate("Test Coordinate", 1, 2, 3, WorldCoordinate.Dimension.OVERWORLD, world));
  }

  @Test
  public void testUpdate() {
    profile.setUsername("John Doe");
    profileRepository.save(profile);

    world.setName("Updated World");
    worldRepository.save(world);

    worldCoordinate.setName("Updated Coordinate");
    worldCoordinateRepository.save(worldCoordinate);

    assertNotNull(profile.getCreatedDate());
    assertNotNull(profile.getLastModifiedDate());
    assertNotNull(world.getCreatedDate());
    assertNotNull(world.getLastModifiedDate());
    assertNotNull(worldCoordinate.getCreatedDate());
    assertNotNull(worldCoordinate.getLastModifiedDate());
  }
}
