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
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@EqualsAndHashCode(callSuper = false)
public class Profile extends Auditable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true)
  @Setter
  private String username;

  @Column(unique = true)
  private String email;

  @OneToMany(mappedBy = "owner")
  @Setter
  private Set<World> ownerWorlds = new HashSet<>();

  @ManyToMany
  @Setter
  private Set<World> editorWorlds = new HashSet<>();

  @ManyToMany
  @Setter
  private Set<World> viewerWorlds = new HashSet<>();

  public Profile(String username, String email) {
    this.username = username;
    this.email = email;
  }

  @Override
  public String toString() {
    return String.format("Profile[id=%d, username='%s', email='%s']", this.id, this.username, this.email);
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
