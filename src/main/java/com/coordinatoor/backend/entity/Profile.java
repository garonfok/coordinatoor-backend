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
@EqualsAndHashCode(callSuper = false, exclude = { "ownerWorlds", "editorWorlds", "viewerWorlds" })
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
  private Set<World> ownerWorlds = new HashSet<>();

  @ManyToMany
  private Set<World> editorWorlds = new HashSet<>();

  @ManyToMany
  private Set<World> viewerWorlds = new HashSet<>();

  public Profile(String username, String email) {
    this.username = username;
    this.email = email;
  }

  @Override
  public String toString() {
    return String.format("Profile[id=%d, username='%s', email='%s']", this.id, this.username, this.email);
  }

  public void addOwnerWorld(World world) {
    this.ownerWorlds.add(world);
    world.setOwner(this);
  }

  public void removeOwnerWorld(World world) {
    this.ownerWorlds.remove(world);
    world.setOwner(null);
  }

  public void addEditorWorld(World world) {
    this.editorWorlds.add(world);
    world.getEditors().add(this);
  }

  public void removeEditorWorld(World world) {
    this.editorWorlds.remove(world);
    world.getEditors().remove(this);
  }

  public void addViewerWorld(World world) {
    this.viewerWorlds.add(world);
    world.getViewers().add(this);
  }

  public void removeViewerWorld(World world) {
    this.viewerWorlds.remove(world);
    world.getViewers().remove(this);
  }
}
