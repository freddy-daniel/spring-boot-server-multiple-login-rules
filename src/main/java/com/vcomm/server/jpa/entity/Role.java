package com.vcomm.server.jpa.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = "roles")
@NamedEntityGraph(
  name = "Role-Graph",
  attributeNodes = {
    @NamedAttributeNode(value = "privileges")
  }
)
public class Role implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;


  @EqualsAndHashCode.Exclude
  @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
  private List<User> users;


  @EqualsAndHashCode.Exclude
  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
    name = "roles_privileges",
    joinColumns = @JoinColumn(
      name = "role_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(
      name = "privilege_id", referencedColumnName = "id"))
  private Set<Privilege> privileges;

}
