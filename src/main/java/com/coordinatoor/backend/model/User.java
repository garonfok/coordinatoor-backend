package com.coordinatoor.backend.model;

import java.util.ArrayList;
import java.util.List;

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
  private List<World> ownerWorlds = new ArrayList<World>();

  @ManyToMany
  private List<World> editorWorlds = new ArrayList<World>();

  @ManyToMany
  private List<World> viewerWorlds = new ArrayList<World>();

  public Long getId() {
    return this.id;
  }

  public String getUsername() {
    return this.username;
  }

  public String getEmail() {
    return this.email;
  }

  public User(String username, String email) {
    this.username = username;
    this.email = email;
  }
}
