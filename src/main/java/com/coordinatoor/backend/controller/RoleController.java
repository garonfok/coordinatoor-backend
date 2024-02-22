package com.coordinatoor.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coordinatoor.backend.entity.Profile;
import com.coordinatoor.backend.entity.World;
import com.coordinatoor.backend.entity.WorldProfile;
import com.coordinatoor.backend.entity.WorldProfile.Role;
import com.coordinatoor.backend.repository.ProfileRepository;
import com.coordinatoor.backend.repository.WorldProfileRepository;
import com.coordinatoor.backend.repository.WorldRepository;

@RestController
@RequestMapping("/role")
public class RoleController {

  @Autowired
  WorldRepository worldRepository;

  @Autowired
  ProfileRepository profileRepository;

  @Autowired
  WorldProfileRepository worldProfileRepository;

  private Pair<World, Profile> checkWorldProfileExists(Long worldId, Long profileId) {
    World world = worldRepository.findById(worldId).orElseThrow(() -> new IllegalArgumentException("World not found"));
    Profile profile = profileRepository.findById(profileId)
        .orElseThrow(() -> new IllegalArgumentException("Profile not found"));
    return Pair.of(world, profile);
  }

  @PutMapping(path = "/owner/{worldId}/{profileId}", produces = "application/json")
  public World updateOwnerRole(Long worldId, Long profileId) {
    Pair<World, Profile> pair = checkWorldProfileExists(worldId, profileId);
    World world = pair.getFirst();
    Profile profile = pair.getSecond();

    Profile owner = profileRepository.findByWorlds_WorldIdAndWorlds_RoleOrderByUsername(worldId, Role.OWNER).stream()
        .findFirst().orElseThrow(() -> new IllegalArgumentException("Owner not found"));

    WorldProfile worldProfileOwner = worldProfileRepository.findByWorldIdAndProfileId(worldId, owner.getId());
    worldProfileOwner.setRole(Role.VIEWER);

    WorldProfile worldProfile = worldProfileRepository.findByWorldIdAndProfileId(worldId, profileId);
    if (worldProfile == null) {
      world.addProfile(profile, Role.OWNER);
    } else {
      worldProfile.setRole(Role.OWNER);
    }

    worldProfileRepository.save(worldProfile);
    worldProfileRepository.save(worldProfileOwner);
    return worldRepository.save(world);
  }

  @PutMapping(path = "/editor/{worldId}/{profileId}", produces = "application/json")
  public World updateEditorRole(Long worldId, Long profileId) {
    Pair<World, Profile> pair = checkWorldProfileExists(worldId, profileId);
    World world = pair.getFirst();
    Profile profile = pair.getSecond();

    WorldProfile worldProfile = worldProfileRepository.findByWorldIdAndProfileId(worldId, profileId);
    if (worldProfile == null) {
      world.addProfile(profile, Role.EDITOR);
    } else {
      if (worldProfile.getRole() == Role.OWNER) {
        throw new IllegalArgumentException("Owner must be reassigned before changing role to editor");
      }
      worldProfile.setRole(Role.EDITOR);
    }

    return worldRepository.save(world);
  }

  @PutMapping(path = "/viewer/{worldId}/{profileId}", produces = "application/json")
  public World updateViewerRole(Long worldId, Long profileId) {
    Pair<World, Profile> pair = checkWorldProfileExists(worldId, profileId);
    World world = pair.getFirst();
    Profile profile = pair.getSecond();

    WorldProfile worldProfile = worldProfileRepository.findByWorldIdAndProfileId(worldId, profileId);
    if (worldProfile == null) {
      world.addProfile(profile, Role.VIEWER);
    } else {
      if (worldProfile.getRole() == Role.OWNER) {
        throw new IllegalArgumentException("Owner must be reassigned before changing role to viewer");
      }
      worldProfile.setRole(Role.VIEWER);
    }

    return worldRepository.save(world);
  }

  @DeleteMapping(path = "/{worldId}/{profileId}", produces = "application/json")
  public void deleteRole(Long worldId, Long profileId) {
    Pair<World, Profile> pair = checkWorldProfileExists(worldId, profileId);
    World world = pair.getFirst();
    Profile profile = pair.getSecond();

    WorldProfile worldProfile = worldProfileRepository.findByWorldIdAndProfileId(worldId, profileId);
    if (worldProfile == null) {
      throw new IllegalArgumentException("Profile not found in world");
    }
    if (worldProfile.getRole() == Role.OWNER) {
      throw new IllegalArgumentException("Owner cannot be removed from world");
    }

    world.removeProfile(profile);
    worldRepository.save(world);
  }

}
