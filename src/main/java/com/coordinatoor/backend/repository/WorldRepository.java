package com.coordinatoor.backend.repository;

import java.util.List;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import com.coordinatoor.backend.entity.World;

@Repository
public interface WorldRepository extends ListCrudRepository<World, Long> {
  List<World> findByNameContainsIgnoreCase(String name);
}
