package com.coordinatoor.backend.repository;

import java.util.List;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import com.coordinatoor.backend.model.Profile;

@Repository
public interface ProfileRepository extends ListCrudRepository<Profile, Long> {
  List<Profile> findByEmailContainingIgnoreCaseOrderByEmailAsc(String email);

  List<Profile> findByUsernameContainingIgnoreCaseOrderByUsernameAsc(String username);
}
