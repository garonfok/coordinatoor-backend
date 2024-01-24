package com.coordinatoor.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;

@Entity
public class WorldUser {

  @Id
  private Long worldId;

  @Id
  private Long userId;

  @Enumerated(EnumType.STRING)
  private RoleEnum role;

  public WorldUser(Long worldId, Long userId, RoleEnum role) {
    this.worldId = worldId;
    this.userId = userId;
    this.role = role;
  }
}

enum RoleEnum {
  OWNER, VIEWER, EDITOR
}
