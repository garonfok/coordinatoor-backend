package com.coordinatoor.backend.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

@Entity
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(unique = true)
  private String username;

  @Column(unique = true)
  private String email;

  @OneToMany(mappedBy = "owner")
  private Set<World> ownerWorlds = new HashSet<>();

  @ManyToMany
  private Set<World> editorWorlds = new HashSet<>();

  @ManyToMany
  private Set<World> viewerWorlds = new HashSet<>();

  public User(String username, String email) {
    this.username = username;
    this.email = email;
  }

  public Long getId() {
    return this.id;
  }

  public String getUsername() {
    return this.username;
  }

  public String getEmail() {
    return this.email;
  }

  public Set<World> getOwnerWorlds() {
    return this.ownerWorlds;
  }

  public Set<World> getEditorWorlds() {
    return this.editorWorlds;
  }

  public Set<World> getViewerWorlds() {
    return this.viewerWorlds;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void addOwnerWorld(World world) {
    this.ownerWorlds.add(world);
  }

  public void addEditorWorld(World world) {
    this.editorWorlds.add(world);
  }

  public void addViewerWorld(World world) {
    this.viewerWorlds.add(world);
  }

  public void removeOwnerWorld(World world) {
    this.ownerWorlds.remove(world);
  }

  public void removeEditorWorld(World world) {
    this.editorWorlds.remove(world);
  }

  public void removeViewerWorld(World world) {
    this.viewerWorlds.remove(world);
  }
}
