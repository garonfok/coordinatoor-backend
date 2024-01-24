package com.coordinatoor.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class World {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String name;

  @Column(nullable = true)
  private String seed;

  @Column(nullable = true)
  private String ipAddress;

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
