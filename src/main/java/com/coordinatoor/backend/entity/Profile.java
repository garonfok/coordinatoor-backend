package com.coordinatoor.backend.entity;

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
public class Profile {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
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

  protected Profile() {}

  public Profile(String username, String email) {
    this.username = username;
    this.email = email;
  }

  @Override
  public String toString() {
    return String.format("Profile[id=%d, username='%s', email='%s']", this.id, this.username, this.email);
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || this.getClass() != o.getClass()) {
      return false;
    }
    Profile profile = (Profile) o;
    return profile.getId().equals(this.id);
  }

  @Override
  public int hashCode() {
    return this.id.hashCode();
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

  public boolean isOwner(World world) {
    return this.ownerWorlds.contains(world);
  }

  public boolean isEditor(World world) {
    return this.editorWorlds.contains(world);
  }

  public boolean isViewer(World world) {
    return this.viewerWorlds.contains(world);
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
