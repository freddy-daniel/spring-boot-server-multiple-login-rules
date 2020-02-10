package com.vcomm.server.dto.user;

import com.vcomm.server.dto.role.RoleDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Set;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {
  String id;

  String username;

  @JsonIgnore
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  String password;

  String firstName;

  String lastName;

  String title;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  String profilePic;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private Set<RoleDTO> roles;


}
