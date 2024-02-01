package com.coordinatoor.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import com.coordinatoor.backend.model.Profile;

@Repository
public interface ProfileRepository extends ListCrudRepository<Profile, Long> {
  Optional<Profile> findByEmail(String email);

  Optional<Profile> findByUsername(String email);

  List<Profile> getAllByUsername(String username);
}
