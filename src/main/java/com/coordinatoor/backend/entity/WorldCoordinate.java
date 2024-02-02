package com.coordinatoor.backend.entity;

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
public class WorldCoordinate extends Auditable {

  public enum DimensionEnum {
    OVERWORLD,
    NETHER,
    END
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
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

  protected WorldCoordinate() {
  }

  public WorldCoordinate(String name, int x, int y, int z, DimensionEnum dimension, World world) {
    this.name = name;
    this.x = x;
    this.y = y;
    this.z = z;
    this.dimension = dimension;
    this.world = world;
  }

  @Override
  public String toString() {
    return String.format("WorldCoordinate[id=%d, name='%s', x='%d', y='%d', z='%d', dimension='%s']", this.id,
        this.name, this.x, this.y, this.z, this.dimension);
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || this.getClass() != o.getClass()) {
      return false;
    }
    WorldCoordinate worldCoordinate = (WorldCoordinate) o;
    return worldCoordinate.getId().equals(this.id);
  }

  @Override
  public int hashCode() {
    return this.id.hashCode();
  }

  public Long getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
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

  public DimensionEnum getDimension() {
    return this.dimension;
  }

  public World getWorld() {
    return this.world;
  }

  public void setName(String name) {
    this.name = name;
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

  public void setDimension(DimensionEnum dimension) {
    this.dimension = dimension;
  }
}
