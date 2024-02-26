package com.coordinatoor.backend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.coordinatoor.backend.entity.Profile;
import com.coordinatoor.backend.entity.World;
import com.coordinatoor.backend.entity.WorldCoordinate;
import com.coordinatoor.backend.entity.WorldCoordinate.Dimension;
import com.coordinatoor.backend.entity.WorldProfile.Role;
import com.coordinatoor.backend.repository.ProfileRepository;
import com.coordinatoor.backend.repository.WorldCoordinateRepository;
import com.coordinatoor.backend.repository.WorldRepository;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
public class RepositoryTests {

  @Autowired
  private ProfileRepository profileRepository;
  @Autowired
  private WorldRepository worldRepository;
  @Autowired
  private WorldCoordinateRepository worldCoordinateRepository;

  private Profile profile1;
  private Profile profile2;
  private Profile profile3;
  private Profile profile4;
  private Profile profile5;
  private World world;
  private WorldCoordinate worldCoordinate1;
  private WorldCoordinate worldCoordinate2;
  private WorldCoordinate worldCoordinate3;

  @BeforeAll
  public void create() {
    profile1 = profileRepository.save(new Profile("1", "johnsmith", "johnsmith@email.com"));
    profile2 = profileRepository.save(new Profile("2", "janedoe", "janedoe@email.com"));
    profile3 = profileRepository.save(new Profile("3", "carlsagan", "carlsagan@email.com"));
    profile4 = profileRepository.save(new Profile("4", "susanwong", "susanwong@email.com"));
    profile5 = profileRepository.save(new Profile("5", "troybaker", "troybaker@email.com"));

    profileRepository.save(profile1);
    profileRepository.save(profile2);
    profileRepository.save(profile3);
    profileRepository.save(profile4);
    profileRepository.save(profile5);

    world = new World("Test World");
    world.setSeed("12345");
    world.setIpAddress("jahskdhaskjdhaks");
    worldRepository.save(world);

    world.addProfile(profile1, Role.OWNER);
    world.addProfile(profile2, Role.EDITOR);
    world.addProfile(profile3, Role.EDITOR);
    world.addProfile(profile4, Role.VIEWER);
    world.addProfile(profile5, Role.VIEWER);

    worldRepository.save(world);

    worldCoordinate1 = new WorldCoordinate("Test Coordinate 1", 0, 0, 0, Dimension.OVERWORLD, world);
    worldCoordinate2 = new WorldCoordinate("Test Coordinate 2", 0, 0, 0, Dimension.NETHER, world);
    worldCoordinate3 = new WorldCoordinate("Test Coordinate 3", 0, 0, 0, Dimension.END, world);

    worldCoordinateRepository.save(worldCoordinate1);
    worldCoordinateRepository.save(worldCoordinate2);
    worldCoordinateRepository.save(worldCoordinate3);
  }

  @Test
  public void testExists() {
    assertTrue(profileRepository.existsById(profile1.getId()));
    assertTrue(profileRepository.existsById(profile2.getId()));
    assertTrue(profileRepository.existsById(profile3.getId()));
    assertTrue(profileRepository.existsById(profile4.getId()));
    assertTrue(profileRepository.existsById(profile5.getId()));
    assertTrue(worldCoordinateRepository.existsById(worldCoordinate1.getId()));
    assertTrue(worldCoordinateRepository.existsById(worldCoordinate2.getId()));
    assertTrue(worldCoordinateRepository.existsById(worldCoordinate3.getId()));
    assertTrue(worldRepository.existsById(world.getId()));
  }

  @Test
  public void testProfile() {
    Profile profile = profileRepository.findById(1L).orElse(null);
    assertNotNull(profile);
    assertEquals("johnsmith", profile.getUsername());

    profile = profileRepository.findByEmail("janedoe@email.com").orElse(null);
    assertNotNull(profile);
    assertEquals("janedoe", profile.getUsername());

    profile = profileRepository.findByAuth0Sub("3").orElse(null);
    assertNotNull(profile);
    assertEquals("carlsagan", profile.getUsername());

    List<Profile> searchedEmails = profileRepository.findByEmailContainsIgnoreCaseOrderByUsername("@email.com");
    assertEquals(5, searchedEmails.size());

    List<Profile> searchedUsernames = profileRepository.findByUsernameContainsIgnoreCaseOrderByUsername("jAnEdOe");
    assertEquals(1, searchedUsernames.size());
    assertEquals(profile2, searchedUsernames.get(0));

    List<Profile> searchedWorlds = profileRepository.findByWorlds_WorldIdAndWorlds_RoleOrderByUsername(world.getId(),
        Role.EDITOR);
    assertEquals(2, searchedWorlds.size());

    searchedWorlds = profileRepository.findByWorlds_WorldIdAndWorlds_RoleOrderByUsername(world.getId(), Role.VIEWER);
    assertEquals(2, searchedWorlds.size());

    searchedWorlds = profileRepository.findByWorlds_WorldIdAndWorlds_RoleOrderByUsername(world.getId(), Role.OWNER);
    assertEquals(1, searchedWorlds.size());
  }

  @Test
  public void testWorld() {
    World world = worldRepository.findById(1L).orElse(null);
    assertNotNull(world);
    assertEquals("Test World", world.getName());
    assertEquals("12345", world.getSeed());

    List<World> searchedWorlds = worldRepository.findByNameContainsIgnoreCase("Test");
    assertEquals(1, searchedWorlds.size());

    searchedWorlds = worldRepository.findByNameContainsIgnoreCase("wOrLd");
    assertEquals(1, searchedWorlds.size());
  }

  @Test
  public void testWorldCoordinate() {
    WorldCoordinate worldCoordinate = worldCoordinateRepository.findById(1L).orElse(null);
    assertNotNull(worldCoordinate);
    assertEquals("Test Coordinate 1", worldCoordinate.getName());
    assertEquals(0, worldCoordinate.getX());
    assertEquals(0, worldCoordinate.getY());
    assertEquals(0, worldCoordinate.getZ());
    assertEquals(Dimension.OVERWORLD, worldCoordinate.getDimension());

    List<WorldCoordinate> searchedCoordinates = worldCoordinateRepository
        .findByWorldAndNameContainsIgnoreCaseOrderByName(world, "Test");
    assertEquals(3, searchedCoordinates.size());

    searchedCoordinates = worldCoordinateRepository.findByWorldAndDimensionOrderByName(world, Dimension.OVERWORLD);
    assertEquals(1, searchedCoordinates.size());

    searchedCoordinates = worldCoordinateRepository.findByWorldAndDimensionAndNameContainsIgnoreCaseOrderByName(world,
        Dimension.OVERWORLD, "Test");
    assertEquals(1, searchedCoordinates.size());

    searchedCoordinates = worldCoordinateRepository.findByWorldOrderByName(world);
    assertEquals(3, searchedCoordinates.size());
  }
}
