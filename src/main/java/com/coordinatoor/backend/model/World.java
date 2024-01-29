package com.coordinatoor.backend.model;

import java.util.ArrayList;
import java.util.List;

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
  private List<WorldCoordinate> coordinates = new ArrayList<WorldCoordinate>();

  @ManyToOne
  @JoinColumn(name = "fk_user")
  private User owner;

  @ManyToMany
  private List<User> editors = new ArrayList<User>();

  @ManyToMany
  private List<User> viewers = new ArrayList<User>();

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

  public List<WorldCoordinate> getCoordinates() {
    return this.coordinates;
  }

  public User getOwner() {
    return this.owner;
  }

  public List<User> getEditors() {
    return this.editors;
  }

  public List<User> getViewers() {
    return this.viewers;
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
