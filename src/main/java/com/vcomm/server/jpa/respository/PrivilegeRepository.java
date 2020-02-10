package com.vcomm.server.jpa.respository;

import com.vcomm.server.jpa.entity.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource(path = "privilege")
public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {
}
