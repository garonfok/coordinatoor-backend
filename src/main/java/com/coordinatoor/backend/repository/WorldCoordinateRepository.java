package com.coordinatoor.backend.repository;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import com.coordinatoor.backend.entity.WorldCoordinate;

@Repository
public interface WorldCoordinateRepository extends ListCrudRepository<WorldCoordinate, Long> {
}
