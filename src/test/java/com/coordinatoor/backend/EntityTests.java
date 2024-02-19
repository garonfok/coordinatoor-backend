package com.coordinatoor.backend;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.coordinatoor.backend.entity.Profile;
import com.coordinatoor.backend.entity.World;
import com.coordinatoor.backend.entity.WorldCoordinate;
import com.coordinatoor.backend.entity.WorldProfile.Role;
import com.coordinatoor.backend.repository.ProfileRepository;
import com.coordinatoor.backend.repository.WorldCoordinateRepository;
import com.coordinatoor.backend.repository.WorldRepository;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
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

  @BeforeAll
  public void create() {
    profile = profileRepository.save(new Profile("johnsmith", "johnsmith@email.com"));

    world = worldRepository.save(
        new World("Test World", "12345", "12334123"));

    worldCoordinate = worldCoordinateRepository.save(
        new WorldCoordinate("Test Coordinate", 1, 2, 3,
            WorldCoordinate.Dimension.OVERWORLD, world));
  }

  @Test
  public void testExists() {
    long profileId = profile.getId();
    long worldId = world.getId();
    long worldCoordinateId = worldCoordinate.getId();

    assertTrue(profileRepository.existsById(profileId));
    assertTrue(worldRepository.existsById(worldId));
    assertTrue(worldCoordinateRepository.existsById(worldCoordinateId));
  }

  @Test
  public void testRoles() {
    Profile profile1 = profileRepository.save(new Profile("janedoe", "janedoe@email.com"));
    Profile profile2 = profileRepository.save(new Profile("jameswong", "jameswong@email.com"));
    Profile profile3 = profileRepository.save(new Profile("jacksmith", "jacksmith@email.com"));
    Profile profile4 = profileRepository.save(new Profile("marysue", "marysue@email.com"));

    world.addProfile(profile1, Role.OWNER);
    world.addProfile(profile2, Role.EDITOR);
    world.addProfile(profile3, Role.VIEWER);
    world.addProfile(profile4, Role.VIEWER);

    assertEquals(4, world.getProfiles().size());
    world.removeProfile(profile4);
    assertEquals(3, world.getProfiles().size());
    world.setRole(profile3, Role.EDITOR);
  }
}
