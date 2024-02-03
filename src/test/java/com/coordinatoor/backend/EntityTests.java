package com.coordinatoor.backend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

  private Profile profile1;
  private Profile profile2;
  private World world;
  private WorldCoordinate worldCoordinate;

  @BeforeEach
  public void create() {
    profile1 = profileRepository.save(new Profile("johnsmith", "johnsmith@email.com"));
    profile2 = profileRepository.save(new Profile("janedoe", "janedoe@email.com"));

    world = worldRepository.save(
        new World("Test World", "12345", "12334123", profile1));

    worldCoordinate = worldCoordinateRepository.save(
        new WorldCoordinate("Test Coordinate", 1, 2, 3,
            WorldCoordinate.DimensionEnum.OVERWORLD, world));
  }

  @Test
  public void testExists() {
    long profileId = profile1.getId();
    long worldId = world.getId();
    long worldCoordinateId = worldCoordinate.getId();

    assertTrue(profileRepository.existsById(profileId));
    assertTrue(worldRepository.existsById(worldId));
    assertTrue(worldCoordinateRepository.existsById(worldCoordinateId));
  }

  @Test
  public void testRoles() {
    assertEquals(profile1, world.getOwner());

    profile1.removeOwnerWorld(world);
    profile2.addOwnerWorld(world);

    assertEquals(profile2, world.getOwner());

    assertEquals(0, profile1.getOwnerWorlds().size());
    assertEquals(1, profile2.getOwnerWorlds().size());

    assertEquals(profile2, world.getOwner());
  }
}
