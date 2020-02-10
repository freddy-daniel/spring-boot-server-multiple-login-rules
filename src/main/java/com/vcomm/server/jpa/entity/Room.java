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
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "rooms")
@Data
@NamedEntityGraph(
    name = "Room-Graph",
    attributeNodes = {
            @NamedAttributeNode(value = "roles", subgraph = "roles")
    },
    subgraphs = @NamedSubgraph(name = "roles", attributeNodes = @NamedAttributeNode(value = "privileges"))
)

public class Room implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Type(type = "uuid-char")
    @Column(name = "id", columnDefinition = "char", updatable = false, nullable = false)
    private UUID id;

    @NotBlank
    @Column(nullable = false)
    String organisationId;

    @NotBlank
    @Column(nullable = false, unique = true)
    String roomNumber;

    @NotBlank
    @Column(nullable = false, unique = true)
    String password;

    @Column
    String devices;

    boolean status = true;

    @EqualsAndHashCode.Exclude
    @LazyCollection(LazyCollectionOption.TRUE)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "rooms_roles",
            joinColumns = @JoinColumn(
                    name = "room_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles;
}
