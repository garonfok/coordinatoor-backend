package com.coordinatoor.backend.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
  private List<Coordinate> coordinates = new ArrayList<Coordinate>();

  public World(String name, String seed, String ipAddress) {
    this.name = name;
    this.seed = seed;
    this.ipAddress = ipAddress;
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

  public Long getId() {
    return this.id;
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
}
