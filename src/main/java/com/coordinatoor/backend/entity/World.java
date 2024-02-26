package com.coordinatoor.backend.entity;

import java.util.HashSet;
import java.util.Set;

import com.coordinatoor.backend.entity.WorldCoordinate.Dimension;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, exclude = { "coordinates", "profiles" })
public class World extends Auditable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @Column(nullable = true)
  private String seed;

  @Column(nullable = true)
  private String ipAddress;

  @OneToMany(mappedBy = "world", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<WorldCoordinate> coordinates = new HashSet<>();

  @OneToMany(mappedBy = "world", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<WorldProfile> profiles = new HashSet<>();

  public World(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return String.format("World[id=%d, name='%s', seed='%s', ipAddress='%s']", this.id, this.name, this.seed,
        this.ipAddress);
  }

  public Set<WorldCoordinate> getCoordinates(Dimension dimension) {
    Set<WorldCoordinate> coordinates = new HashSet<>();

    for (WorldCoordinate coordinate : this.coordinates) {
      if (coordinate.getDimension().equals(dimension)) {
        coordinates.add(coordinate);
      }
    }

    return coordinates;
  }

  public void addCoordinate(WorldCoordinate coordinate) {
    this.coordinates.add(coordinate);
    coordinate.setWorld(this);
  }

  public void removeCoordinate(WorldCoordinate coordinate) {
    this.coordinates.remove(coordinate);
    coordinate.setWorld(null);
  }

  public void addProfile(Profile profile, WorldProfile.Role role) {
    WorldProfile worldProfile = new WorldProfile(this, profile, role);
    this.profiles.add(worldProfile);
    profile.getWorlds().add(worldProfile);
  }

  public void removeProfile(Profile profile) {
    for (WorldProfile worldProfile : this.profiles) {
      if (worldProfile.getProfile().equals(profile)) {
        this.profiles.remove(worldProfile);
        worldProfile.getProfile().getWorlds().remove(worldProfile);
        worldProfile.setWorld(null);
        worldProfile.setProfile(null);
      }
    }
  }

  public void setRole(Profile profile, WorldProfile.Role role) {
    for (WorldProfile worldProfile : this.profiles) {
      if (worldProfile.getProfile().equals(profile)) {
        worldProfile.setRole(role);
      }
    }
  }
}
