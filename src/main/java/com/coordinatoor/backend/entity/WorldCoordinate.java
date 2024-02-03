package com.coordinatoor.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@EqualsAndHashCode(callSuper = false)
public class WorldCoordinate extends Auditable {

  public enum DimensionEnum {
    OVERWORLD,
    NETHER,
    END
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Setter
  private String name;

  @Setter
  private int x;

  @Setter
  private int y;

  @Setter
  private int z;

  @Enumerated(EnumType.STRING)
  @Column(columnDefinition = "varchar(255) default 'OVERWORLD'")
  @Setter
  private DimensionEnum dimension;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "fk_world")
  @Setter
  private World world;

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
}
