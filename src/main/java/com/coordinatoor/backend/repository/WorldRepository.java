package com.coordinatoor.backend.repository;

import java.util.List;

import org.springframework.data.repository.ListCrudRepository;

import com.coordinatoor.backend.model.World;

public interface WorldRepository extends ListCrudRepository<World, Long> {
  List<World> findByName(String name);
}
