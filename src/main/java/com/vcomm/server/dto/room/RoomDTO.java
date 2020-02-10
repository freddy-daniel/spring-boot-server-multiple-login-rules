package com.vcomm.server.dto.room;

import com.vcomm.server.dto.role.RoleDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Set;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoomDTO {
  String id;

  String organisationId;

  String roomNumber;

  @JsonIgnore
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  String password;

  String devices;

  boolean status;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private Set<RoleDTO> roles;

}
