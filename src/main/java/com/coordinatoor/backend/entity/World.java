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

@Entity
public class World extends Auditable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @Column(nullable = true)
  private String seed;

  @Column(nullable = true)
  private String ipAddress;

  @OneToMany(mappedBy = "world")
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Set<WorldCoordinate> coordinates = new HashSet<>();

  @ManyToOne
  @JoinColumn(name = "fk_user")
  private Profile owner;

  @ManyToMany
  private Set<Profile> editors = new HashSet<>();

  @ManyToMany
  private Set<Profile> viewers = new HashSet<>();

  protected World() {
  }

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

  @Override
  public boolean equals(Object o) {
    if (o == null || this.getClass() != o.getClass()) {
      return false;
    }
    World world = (World) o;
    return world.getId().equals(this.id);
  }

  @Override
  public int hashCode() {
    return this.id.hashCode();
  }

  public Long getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public String getSeed() {
    return this.seed;
  }

  public String getIpAddress() {
    return this.ipAddress;
  }

  public Set<WorldCoordinate> getCoordinates() {
    return this.coordinates;
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

  public Profile getOwner() {
    return this.owner;
  }

  public Set<Profile> getEditors() {
    return this.editors;
  }

  public Set<Profile> getViewers() {
    return this.viewers;
  }

  public boolean isOwner(Profile profile) {
    return this.owner.equals(profile);
  }

  public boolean isEditor(Profile profile) {
    return this.editors.contains(profile);
  }

  public boolean isViewer(Profile profile) {
    return this.viewers.contains(profile);
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setSeed(String seed) {
    this.seed = seed;
  }

  public void setIpAddress(String ipAddress) {
    this.ipAddress = ipAddress;
  }

  public void setOwner(Profile owner) {
    this.owner = owner;
  }

  public void addEditor(Profile editor) {
    if (this.owner.equals(editor)) {
      throw new IllegalArgumentException("Owner cannot be added as editor");
    }

    this.editors.add(editor);
  }

  public void removeEditor(Profile editor) {
    this.editors.remove(editor);
  }

  public void addViewer(Profile viewer) {
    if (this.owner.equals(viewer)) {
      throw new IllegalArgumentException("Owner cannot be added as viewer");
    }

    this.viewers.add(viewer);
  }

  public void removeViewer(Profile viewer) {
    this.viewers.remove(viewer);
  }
}
