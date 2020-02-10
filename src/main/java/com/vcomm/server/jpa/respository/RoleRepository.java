package com.vcomm.server.jpa.respository;

import com.vcomm.server.jpa.entity.Role;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

  @EntityGraph("Role-Graph")
  @Override
  List<Role> findAll();

  @EntityGraph("Role-Graph")
  @Override
  Optional<Role> findById(@Param("id") Long id);

  @EntityGraph("Role-Graph")
  Optional<Role> findByName(@Param("name") String name);
}
