package com.coordinatoor.backend.repository;

import java.util.List;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import com.coordinatoor.backend.entity.World;
import com.coordinatoor.backend.entity.WorldCoordinate;
import com.coordinatoor.backend.entity.WorldCoordinate.Dimension;

@Repository
public interface WorldCoordinateRepository extends ListCrudRepository<WorldCoordinate, Long> {

  public List<WorldCoordinate> findByWorldOrderByName(World world);

  public List<WorldCoordinate> findByWorldAndDimensionOrderByName(World world, Dimension dimension);

  public List<WorldCoordinate> findByWorldAndDimensionAndNameContainsIgnoreCaseOrderByName(World world,
      Dimension dimension,
      String name);

  public List<WorldCoordinate> findByWorldAndNameContainsIgnoreCaseOrderByName(World world, String name);
}
