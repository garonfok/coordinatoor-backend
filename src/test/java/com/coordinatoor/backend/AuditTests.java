package com.coordinatoor.backend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

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
import com.coordinatoor.backend.repository.ProfileRepository;
import com.coordinatoor.backend.repository.WorldCoordinateRepository;
import com.coordinatoor.backend.repository.WorldRepository;
import com.github.javafaker.Faker;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
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

  private Faker faker;

  private static final int COORD_LOWER_BOUND = -29999999;
  private static final int COORD_UPPER_BOUND = 29999999;

  private static final int UPDATE_COUNT = 200;

  @BeforeAll
  public void setup() {
    faker = new Faker();

    profile = new Profile(
        faker.internet().uuid(),
        faker.name().username(),
        faker.internet().uuid());
    world = new World(faker.name().title());
    worldCoordinate = new WorldCoordinate(
        faker.book().title(),
        faker.number().numberBetween(COORD_LOWER_BOUND, COORD_UPPER_BOUND),
        faker.number().numberBetween(-64, 320),
        faker.number().numberBetween(COORD_LOWER_BOUND, COORD_UPPER_BOUND), Dimension.OVERWORLD, world);

    profileRepository.save(profile);
    worldRepository.save(world);
    worldCoordinateRepository.save(worldCoordinate);
  }

  @Test
  public void testCreate() {
    assertNotNull(profile.getCreatedDate());
    assertNotNull(profile.getLastModifiedDate());
    assertNotNull(world.getCreatedDate());
    assertNotNull(world.getLastModifiedDate());
    assertNotNull(worldCoordinate.getCreatedDate());
    assertNotNull(worldCoordinate.getLastModifiedDate());

    LocalDateTime profileLastModifiedDate = profile.getLastModifiedDate();
    LocalDateTime profileCreatedDate = profile.getCreatedDate();

    LocalDateTime worldLastModifiedDate = world.getLastModifiedDate();
    LocalDateTime worldCreatedDate = world.getCreatedDate();

    LocalDateTime worldCoordinateLastModifiedDate = worldCoordinate.getLastModifiedDate();
    LocalDateTime worldCoordinateCreatedDate = worldCoordinate.getCreatedDate();

    assertEquals(worldCoordinateLastModifiedDate, worldCoordinateCreatedDate);
    assertEquals(worldLastModifiedDate, worldCreatedDate);
    assertEquals(profileLastModifiedDate, profileCreatedDate);
  }

  @Test
  void testUpdate() {

    LocalDateTime profileBeforeUpdateDate;
    LocalDateTime worldBeforeUpdateDate;
    LocalDateTime worldCoordinateBeforeUpdateDate;

    LocalDateTime profileAfterUpdateDate;
    LocalDateTime worldAfterUpdateDate;
    LocalDateTime worldCoordinateAfterUpdateDate;

    for (int i = 0; i < UPDATE_COUNT; i++) {

      profileBeforeUpdateDate = profile.getLastModifiedDate();
      worldBeforeUpdateDate = world.getLastModifiedDate();
      worldCoordinateBeforeUpdateDate = worldCoordinate.getLastModifiedDate();

      profile.setUsername(faker.name().username());
      profile.setEmail(faker.internet().emailAddress());
      profileRepository.save(profile);

      world.setName(faker.name().title());
      world.setSeed(faker.leagueOfLegends().quote());
      world.setIpAddress(faker.internet().ipV4Address().toString());
      worldRepository.save(world);

      worldCoordinate.setName(faker.book().title());
      worldCoordinate.setX(faker.number().numberBetween(COORD_LOWER_BOUND, COORD_UPPER_BOUND));
      worldCoordinate.setY(faker.number().numberBetween(-64, 320));
      worldCoordinate.setZ(faker.number().numberBetween(COORD_LOWER_BOUND, COORD_UPPER_BOUND));
      worldCoordinateRepository.save(worldCoordinate);

      profileAfterUpdateDate = profile.getLastModifiedDate();
      worldAfterUpdateDate = world.getLastModifiedDate();
      worldCoordinateAfterUpdateDate = worldCoordinate.getLastModifiedDate();

      assertTrue(
          profileBeforeUpdateDate.isBefore(profileAfterUpdateDate)
              || profileBeforeUpdateDate.isEqual(profileAfterUpdateDate));
      assertTrue(
          worldBeforeUpdateDate.isBefore(worldAfterUpdateDate)
              || worldBeforeUpdateDate.isEqual(worldAfterUpdateDate));
      assertTrue(
          worldCoordinateBeforeUpdateDate.isBefore(worldCoordinateAfterUpdateDate)
              || worldCoordinateBeforeUpdateDate.isEqual(worldCoordinateAfterUpdateDate));

    }

  }
}
