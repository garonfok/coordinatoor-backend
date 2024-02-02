package com.coordinatoor.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import com.coordinatoor.backend.entity.World;

@Repository
public interface WorldRepository extends ListCrudRepository<World, Long> {

  @Query("""
      SELECT p
      FROM Profile p
      WHERE p.name
        LIKE %:name%
      ORDER BY p.name ASC
        """)
  List<World> findByNameContains(String name);
}
