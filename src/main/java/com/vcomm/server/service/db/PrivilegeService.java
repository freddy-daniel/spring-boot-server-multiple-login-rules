package com.vcomm.server.service.db;

import com.vcomm.server.jpa.entity.Privilege;
import com.vcomm.server.jpa.respository.PrivilegeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrivilegeService {

  @Autowired
  PrivilegeRepository privilegeRepository;

  public List<Privilege> findAll() {
    return privilegeRepository.findAll();
  }

  Privilege findById(String privilegeID) {
    return privilegeRepository
      .findById(Long.valueOf(privilegeID))
      .orElseThrow(() -> new UnsupportedOperationException("Privilege " + privilegeID + " does not exist."));
  }
}
