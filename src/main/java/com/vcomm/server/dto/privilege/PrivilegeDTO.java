package com.vcomm.server.dto.privilege;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PrivilegeDTO {
  private Long id;

  private String name;
}
