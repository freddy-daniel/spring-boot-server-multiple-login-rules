package com.vcomm.server.jpa.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@Table(name = "privileges")
public class Privilege implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @EqualsAndHashCode.Exclude
  @ManyToMany(mappedBy = "privileges", fetch = FetchType.LAZY)
  private List<Role> roles;
}