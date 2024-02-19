package com.coordinatoor.backend.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = false, exclude = { "worlds" })
public class Profile extends Auditable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true)
  private String username;

  @Column(unique = true)
  private String email;

  @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<WorldProfile> worlds = new HashSet<>();

  public Profile(String username, String email) {
    this.username = username;
    this.email = email;
  }

  @Override
  public String toString() {
    return String.format("Profile[id=%d, username='%s', email='%s']", this.id, this.username, this.email);
  }
}
