package com.vcomm.server.service.db;

import com.vcomm.server.service.db.PrivilegeService;
import com.vcomm.server.dto.role.RoleDTO;
import com.vcomm.server.jpa.entity.Privilege;
import com.vcomm.server.jpa.entity.Role;
import com.vcomm.server.jpa.respository.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolesService {
  @Autowired
  RoleRepository roleRepository;

  @Autowired
  ModelMapper modelMapper;

  @Autowired
  PrivilegeService privilegeService;

  public List<Role> findAll() {
    return roleRepository.findAll();
  }

  public Role findById(String id) {
    return roleRepository.findById(Long.valueOf(id)).orElseThrow(() -> new UnsupportedOperationException("Role not found."));
  }

  public Role findByName(String name) {
    return roleRepository.findByName(name).orElseThrow(() -> new UnsupportedOperationException("Role not found."));
  }

  public Role save(RoleDTO roleDTO) {
    Role role = modelMapper.map(roleDTO, Role.class);
    return roleRepository.save(role);
  }

  public Role update(String id, RoleDTO roleDTO) {
    Role role = findById(id);
    modelMapper.map(roleDTO, role);
    return roleRepository.save(role);
  }

  public void delete(String id) {
    Role role = findById(id);
    roleRepository.delete(role);
  }

  public Role addPrivilege(String roleID, String privilegeID) {
    Role role = findById(roleID);
    Privilege privilege = privilegeService.findById(privilegeID);
    role
            .getPrivileges()
            .add(privilege);

    return roleRepository.save(role);
  }

  public Role deletePrivilege(String roleID, String privilegeID) {
    Role role = findById(roleID);
    Privilege searchPrivilege = privilegeService.findById(privilegeID);

    role
            .getPrivileges()
            .stream()
            .filter(privilege -> privilege.getId().equals(searchPrivilege.getId()))
            .findFirst()
            .ifPresent(privilege -> role.getPrivileges().remove(privilege));

    return roleRepository.save(role);
  }
}
