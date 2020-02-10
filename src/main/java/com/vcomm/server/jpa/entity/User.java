package com.vcomm.server.jpa.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users")
@Data
@NamedEntityGraph(
  name = "User-Graph",
  attributeNodes = {
    @NamedAttributeNode(value = "roles", subgraph = "roles")
  },
  subgraphs = @NamedSubgraph(name = "roles", attributeNodes = @NamedAttributeNode(value = "privileges"))
)

public class User implements Serializable{

  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(
    name = "UUID",
    strategy = "org.hibernate.id.UUIDGenerator"
  )
  @Type(type = "uuid-char")
  @Column(name = "id", columnDefinition = "char" , updatable = false, nullable = false)
  private UUID id;

  @NotBlank
  @Column(nullable = false, unique = true)
  String username;

  @NotBlank
  String password;

  String firstName;

  String lastName;

  String title;

  String profilePic;

  @EqualsAndHashCode.Exclude
  @LazyCollection(LazyCollectionOption.TRUE)
  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
    name = "users_roles",
    joinColumns = @JoinColumn(
      name = "user_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(
      name = "role_id", referencedColumnName = "id"))
  private Set<Role> roles;

}
