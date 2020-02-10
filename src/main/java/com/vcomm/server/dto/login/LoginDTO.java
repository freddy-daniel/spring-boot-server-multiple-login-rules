package com.vcomm.server.dto.login;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginDTO {

  @NotBlank(message = "username may not be empty")
  String username;

  @NotBlank(message = "password may not be empty")
  String password;
}
