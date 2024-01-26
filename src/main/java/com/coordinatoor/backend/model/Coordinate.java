package com.coordinatoor.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Coordinate {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String name;

  private int x;

  private int y;

  private int z;

  @Enumerated(EnumType.STRING)
  @Column(columnDefinition = "varchar(255) default 'OVERWORLD'")
  private DimensionEnum dimension;

  @ManyToOne
  @JoinColumn(name = "fk_world")
  private World world;

  public Coordinate(String name, int x, int y, int z, DimensionEnum dimension) {
    this.name = name;
    this.x = x;
    this.y = y;
    this.z = z;
    this.dimension = dimension;
  }

  public int getX() {
    return this.x;
  }

  public int getY() {
    return this.y;
  }

  public int getZ() {
    return this.z;
  }

  public void setX(int x) {
    this.x = x;
  }

  public void setY(int y) {
    this.y = y;
  }

  public void setZ(int z) {
    this.z = z;
  }
}

enum DimensionEnum {
  OVERWORLD,
  NETHER,
  END
}
