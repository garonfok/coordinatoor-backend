package com.coordinatoor.backend;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import com.coordinatoor.backend.entity.Profile;
import com.coordinatoor.backend.entity.World;
import com.coordinatoor.backend.entity.WorldCoordinate;
import com.github.javafaker.Faker;

import jakarta.transaction.Transactional;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
public class EntityTests {

  @Autowired
  private TestEntityManager entityManager;

  private Faker faker;

  private Profile[] profiles;
  private World[] worlds;
  private WorldCoordinate[] worldCoordinates;

  private World world_WorldCoordinate;

  private static final int NUM_PROFILES = 100;
  private static final int NUM_WORLDS = 100;
  private static final int NUM_COORDINATES = 100;

  private static final int COORD_LOWER_BOUND = -29999999;
  private static final int COORD_UPPER_BOUND = 29999999;

  @BeforeAll
  @Transactional
  public void setup() {
    faker = new Faker();

    profiles = new Profile[NUM_PROFILES];
    worlds = new World[NUM_WORLDS];
    worldCoordinates = new WorldCoordinate[NUM_COORDINATES];

    for (int i = 0; i < NUM_PROFILES; i++) {
      String auth0Sub = faker.internet().uuid() + i;
      String username = faker.name().username();
      String uuid = Integer.toString(i);
      Profile profile = new Profile(
          auth0Sub,
          username,
          uuid);
      profile.setCreatedDate(LocalDateTime.now());
      profiles[i] = profile;
    }

    for (int i = 0; i < NUM_WORLDS; i++) {
      String name = faker.name().title();
      String seed = faker.leagueOfLegends().quote();
      String ip = faker.internet().ipV4Address().toString();
      World world = new World(name);
      world.setSeed(seed);
      world.setIpAddress(ip);
      world.setCreatedDate(LocalDateTime.now());
      worlds[i] = world;
    }

    world_WorldCoordinate = new World(faker.name().title());
    world_WorldCoordinate.setSeed(faker.leagueOfLegends().quote());
    world_WorldCoordinate.setIpAddress(faker.internet().ipV4Address().toString());
    world_WorldCoordinate.setCreatedDate(LocalDateTime.now());

    for (int i = 0; i < NUM_COORDINATES; i++) {
      String name = faker.book().title();
      int x = faker.number().numberBetween(COORD_LOWER_BOUND, COORD_UPPER_BOUND);
      int y = faker.number().numberBetween(-64, 320);
      int z = faker.number().numberBetween(COORD_LOWER_BOUND, COORD_UPPER_BOUND);
      WorldCoordinate worldCoordinate = new WorldCoordinate(
          name,
          x,
          y,
          z,
          WorldCoordinate.Dimension.OVERWORLD,
          world_WorldCoordinate);
      worldCoordinate.setCreatedDate(LocalDateTime.now());
      worldCoordinates[i] = worldCoordinate;
    }
  }

  @AfterEach
  public void cleanUp() {
    entityManager.clear();
    System.out.println("CLEANED UP");
  }

  @Test
  public void testProfile() {
    for (Profile profile : profiles) {
      Profile savedProfile = entityManager.persistFlushFind(profile);
      assertEquals(profile, savedProfile);
    }
  }

  @Test
  public void testWorld() {
    for (World world : worlds) {
      World savedWorld = entityManager.persistFlushFind(world);
      assertEquals(world, savedWorld);
    }
  }

  @Test
  public void testWorldCoordinate() {
    entityManager.persistAndFlush(world_WorldCoordinate);
    for (WorldCoordinate worldCoordinate : worldCoordinates) {
      WorldCoordinate savedWorldCoordinate = entityManager.persistFlushFind(worldCoordinate);
      assertEquals(worldCoordinate, savedWorldCoordinate);
    }
  }
}
