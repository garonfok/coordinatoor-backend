package com.coordinatoor.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import com.coordinatoor.backend.entity.Profile;

@Repository
public interface ProfileRepository extends ListCrudRepository<Profile, Long> {
  Optional<Profile> findByEmail(String email);

  @Query(value = """
      SELECT p
      FROM Profile p
      WHERE p.email
        LIKE %:email%
      ORDER BY p.email ASC
      """, nativeQuery = true)
  List<Profile> findByEmailContains(String email);

  @Query(value = """
      SELECT p
      FROM Profile p
      WHERE p.username
        LIKE %:username%
      ORDER BY p.username ASC
        """, nativeQuery = true)
  List<Profile> findByUsernameContains(String username);
}
