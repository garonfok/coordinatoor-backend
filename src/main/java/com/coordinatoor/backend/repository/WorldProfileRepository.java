package com.coordinatoor.backend.repository;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import com.coordinatoor.backend.entity.WorldProfile;

@Repository
public interface WorldProfileRepository extends ListCrudRepository<WorldProfile, Long> {

  public WorldProfile findByWorldIdAndProfileId(Long worldId, Long profileId);
}
