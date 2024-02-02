package com.coordinatoor.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import com.coordinatoor.backend.entity.Profile;
import com.coordinatoor.backend.entity.World;

@Repository
public interface ProfileRepository extends ListCrudRepository<Profile, Long> {
  List<Profile> findByEmailContainingIgnoreCaseOrderByEmailAsc(String email);

  List<Profile> findByUsernameContainingIgnoreCaseOrderByUsernameAsc(String username);

  @Query("""
      SELECT p
      FROM Profile p
      JOIN p.editorWorlds w
      WHERE w = :world
      ORDER BY p.username ASC
      """)
  List<Profile> findAllEditorByWorld(World world);

  @Query("""
      SELECT p
      FROM Profile p
      JOIN p.viewerWorlds w
      WHERE w = :world
      ORDER BY p.username ASC
      """)
  List<Profile> findAllViewerByWorld(World world);
}
