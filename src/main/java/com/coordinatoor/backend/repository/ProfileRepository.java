package com.coordinatoor.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import com.coordinatoor.backend.entity.Profile;
import com.coordinatoor.backend.entity.World;

@Repository
public interface ProfileRepository extends ListCrudRepository<Profile, Long> {
  Optional<Profile> findByEmail(String email);

  @Query("""
      SELECT p
      FROM Profile p
      WHERE p.email
        LIKE %:email%
      ORDER BY p.email ASC
      """)
  List<Profile> findByEmailContains(String email);

  @Query("""
      SELECT p
      FROM Profile p
      WHERE p.username
        LIKE %:username%
      ORDER BY p.username ASC
        """)
  List<Profile> findByUsernameContains(String username);

  @Query("""
      SELECT p
      FROM Profile p
      JOIN p.ownerWorlds w
      WHERE w = :world
      ORDER BY p.username ASC
      """)
  Profile findByOwner(World world);

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
