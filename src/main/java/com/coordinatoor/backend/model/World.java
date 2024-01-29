package com.coordinatoor.backend.model;

import java.util.HashSet;
import java.util.Set;

import com.coordinatoor.backend.model.WorldCoordinate.DimensionEnum;

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
public class World {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String name;

  @Column(nullable = true)
  private String seed;

  @Column(nullable = true)
  private String ipAddress;

  @OneToMany(mappedBy = "world")
  private Set<WorldCoordinate> coordinates = new HashSet<>();

  @ManyToOne
  @JoinColumn(name = "fk_user")
  private User owner;

  @ManyToMany
  private Set<User> editors = new HashSet<>();

  @ManyToMany
  private Set<User> viewers = new HashSet<>();

  public World(String name, String seed, String ipAddress, User owner) {
    this.name = name;
    this.seed = seed;
    this.ipAddress = ipAddress;
    this.owner = owner;
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

  public User getOwner() {
    return this.owner;
  }

  public Set<User> getEditors() {
    return this.editors;
  }

  public Set<User> getViewers() {
    return this.viewers;
  }

  public boolean isOwner(User user) {
    return this.owner.equals(user);
  }

  public boolean isEditor(User user) {
    return this.editors.contains(user);
  }

  public boolean isViewer(User user) {
    return this.viewers.contains(user);
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

  public void setOwner(User owner) {
    this.owner = owner;
  }

  public void addEditor(User editor) {
    if (this.owner.equals(editor)) {
      throw new IllegalArgumentException("Owner cannot be added as editor");
    }

    this.editors.add(editor);
  }

  public void removeEditor(User editor) {
    this.editors.remove(editor);
  }

  public void addViewer(User viewer) {
    if (this.owner.equals(viewer)) {
      throw new IllegalArgumentException("Owner cannot be added as viewer");
    }

    this.viewers.add(viewer);
  }

  public void removeViewer(User viewer) {
    this.viewers.remove(viewer);
  }
}
