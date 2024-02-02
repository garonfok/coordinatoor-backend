package com.coordinatoor.backend.repository;

import java.util.List;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import com.coordinatoor.backend.entity.World;
import com.coordinatoor.backend.entity.WorldCoordinate;
import com.coordinatoor.backend.entity.WorldCoordinate.DimensionEnum;

@Repository
public interface WorldCoordinateRepository extends ListCrudRepository<WorldCoordinate, Long> {

  public List<WorldCoordinate> findAllByWorldOrderByName(World world);

  public List<WorldCoordinate> findAllByWorldAndDimensionOrderByName(World world, DimensionEnum dimension);

}
