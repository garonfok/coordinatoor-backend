package com.coordinatoor.backend.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;

@Entity
public class WorldUser {

  @EmbeddedId
  private WorldUserId id;

  @ManyToOne
  @MapsId("worldId")
  @JoinColumn(name = "fk_world")
  private World world;

  @ManyToOne
  @MapsId("userId")
  @JoinColumn(name = "fk_user")
  private User user;

  @Enumerated(EnumType.STRING)
  @Column(columnDefinition = "varchar(255) default 'VIEWER'")
  private RoleEnum role;

  public WorldUser(World world, User user, RoleEnum role) {
    this.world = world;
    this.user = user;
    this.role = role;
    this.id = new WorldUserId(world.getId(), user.getId());
  }

  public World getWorld() {
    return this.world;
  }

  public User getUser() {
    return this.user;
  }

  public RoleEnum getRole() {
    return this.role;
  }

  public void setRole(RoleEnum role) {
    this.role = role;
  }
}

@Embeddable
class WorldUserId implements Serializable {
  private Long worldId;

  private Long userId;

  public WorldUserId(Long worldId, Long userId) {
    this.worldId = worldId;
    this.userId = userId;
  }

  public Long getWorldId() {
    return worldId;
  }

  public Long getUserId() {
    return userId;
  }

  public void setWorldId(Long worldId) {
    this.worldId = worldId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (!(o instanceof WorldUserId))
      return false;
    WorldUserId that = (WorldUserId) o;
    return worldId.equals(that.worldId) && userId.equals(that.userId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(worldId, userId);
  }
}

enum RoleEnum {
  OWNER, VIEWER, EDITOR
}
