package com.vcomm.server.controller.app;

import com.vcomm.server.configuration.Constant;
import com.vcomm.server.dto.login.LoginDTO;
import com.vcomm.server.dto.login.RoomLoginDTO;
import com.vcomm.server.userdetails.room.RoomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping(value = {Constant.API_V1})
public class AccessController {

    @Autowired
    RoomUserDetailsService roomUserDetailsService;

    @Autowired
    @Qualifier("roomAuthenticationProvider")
    AuthenticationProvider authenticationProvider;

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void login(@Valid @RequestBody RoomLoginDTO roomLoginDTO) {
        UserDetails userDetails = roomUserDetailsService.loadUserByUsername(roomLoginDTO.getOrganisationId().concat(roomLoginDTO.getRoomNumber()));

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, roomLoginDTO.getPassword(), userDetails.getAuthorities());
        Authentication auth = authenticationProvider.authenticate(usernamePasswordAuthenticationToken);

        SecurityContextHolder.getContext().setAuthentication(auth);
    }
}
