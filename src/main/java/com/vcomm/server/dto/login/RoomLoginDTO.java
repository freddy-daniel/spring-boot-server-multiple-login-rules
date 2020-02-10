package com.vcomm.server.dto.login;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoomLoginDTO {

    @NotBlank(message = "organisationId may not be empty")
    String organisationId;

    @NotBlank(message = "roomNumber may not be empty")
    String roomNumber;

    @NotBlank(message = "password may not be empty")
    String password;
}
