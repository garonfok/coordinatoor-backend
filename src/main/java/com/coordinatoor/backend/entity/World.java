package com.coordinatoor.backend.entity;

import java.util.HashSet;
import java.util.Set;

import com.coordinatoor.backend.entity.WorldCoordinate.DimensionEnum;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, exclude = { "coordinates", "editors", "viewers" })
public class World extends Auditable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Setter
  private String name;

  @Column(nullable = true)
  @Setter
  private String seed;

  @Column(nullable = true)
  @Setter
  private String ipAddress;

  @OneToMany(mappedBy = "world", cascade = CascadeType.ALL, orphanRemoval = true)
  @Setter
  private Set<WorldCoordinate> coordinates = new HashSet<>();

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "fk_user")
  @Setter
  private Profile owner;

  @ManyToMany
  private Set<Profile> editors = new HashSet<>();

  @ManyToMany
  private Set<Profile> viewers = new HashSet<>();

  public World(String name, String seed, String ipAddress, Profile owner) {
    this.name = name;
    this.seed = seed;
    this.ipAddress = ipAddress;
    this.owner = owner;
  }

  @Override
  public String toString() {
    return String.format("World[id=%d, name='%s', seed='%s', ipAddress='%s']", this.id, this.name, this.seed,
        this.ipAddress);
  }

  public Set<WorldCoordinate> getCoordinates(DimensionEnum dimension) {
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

  public void addEditor(Profile profile) {
    this.editors.add(profile);
    profile.getEditorWorlds().add(this);
  }

  public void removeEditor(Profile profile) {
    this.editors.remove(profile);
    profile.getEditorWorlds().remove(this);
  }

  public void addViewer(Profile profile) {
    this.viewers.add(profile);
    profile.getViewerWorlds().add(this);
  }

  public void removeViewer(Profile profile) {
    this.viewers.remove(profile);
    profile.getViewerWorlds().remove(this);
  }
}
