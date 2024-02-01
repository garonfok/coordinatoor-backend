package com.coordinatoor.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import com.coordinatoor.backend.model.User;

@Repository
public interface UserRepository extends ListCrudRepository<User, Long> {
  Optional<User> findByEmail(String email);

  Optional<User> findByUsername(String email);

  List<User> getAllByUsername(String username);
}
