package com.vcomm.server.dto.role;

import com.vcomm.server.dto.privilege.PrivilegeDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Set;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoleDTO {
  private Long id;

  private String name;

  Set<PrivilegeDTO> privileges;
}
