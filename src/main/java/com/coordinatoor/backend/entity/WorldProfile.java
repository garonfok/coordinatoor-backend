package com.coordinatoor.backend.entity;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class WorldProfile extends Auditable {

  public enum RoleEnum {
    OWNER, EDITOR, VIEWER
  }

  @EmbeddedId
  private WorldProfileId id;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("worldId")
  private World world;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("profileId")
  private Profile profile;

  @Column(nullable = false)
  private RoleEnum role;

  public WorldProfile(World world, Profile profile, RoleEnum role) {
    this.world = world;
    this.profile = profile;
    this.id = new WorldProfileId(world.getId(), profile.getId());
    this.role = role;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    WorldProfile that = (WorldProfile) o;
    return Objects.equals(world, that.world) && Objects.equals(profile, that.profile);
  }

  @Override
  public int hashCode() {
    return Objects.hash(world, profile);
  }
}

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
class WorldProfileId implements Serializable {
  @Column
  private Long worldId;

  @Column
  private Long profileId;

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    WorldProfileId that = (WorldProfileId) o;
    return Objects.equals(worldId, that.worldId) && Objects.equals(profileId, that.profileId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(worldId, profileId);
  }
}
