package com.coordinatoor.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import com.coordinatoor.backend.entity.Profile;
import com.coordinatoor.backend.entity.WorldProfile.Role;

@Repository
public interface ProfileRepository extends ListCrudRepository<Profile, Long> {
  Optional<Profile> findByEmail(String email);

  Optional<Profile> findByAuth0Sub(String sub);

  List<Profile> findByEmailContainsIgnoreCaseOrderByUsername(String email);

  List<Profile> findByUsernameContainsIgnoreCaseOrderByUsername(String username);

  List<Profile> findByWorlds_WorldIdAndWorlds_RoleOrderByUsername(Long worldId, Role role);
}
