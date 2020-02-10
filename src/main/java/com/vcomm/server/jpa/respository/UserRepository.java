package com.vcomm.server.jpa.respository;

import com.vcomm.server.jpa.entity.Role;
import com.vcomm.server.jpa.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RepositoryRestResource(path = "users")
public interface UserRepository extends JpaRepository<User,String> {

  @EntityGraph("User-Graph")
  Optional<User> findUserByUsername(@Param("username") String username);

  @EntityGraph("User-Graph")
  Optional<User> findById(@Param("id") UUID id);

  @EntityGraph("User-Graph")
  @Override
  List<User> findAll();

}
