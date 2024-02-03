package com.coordinatoor.backend.entity;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.coordinatoor.backend.entity.WorldCoordinate.DimensionEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@EqualsAndHashCode(callSuper = false)
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

  @OneToMany(mappedBy = "world")
  @OnDelete(action = OnDeleteAction.CASCADE)
  @Setter
  private Set<WorldCoordinate> coordinates = new HashSet<>();

  @ManyToOne
  @JoinColumn(name = "fk_user")
  @Setter
  private Profile owner;

  @ManyToMany
  @Setter
  private Set<Profile> editors = new HashSet<>();

  @ManyToMany
  @Setter
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
}
