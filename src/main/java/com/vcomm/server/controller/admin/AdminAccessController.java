package com.vcomm.server.controller.admin;

import com.vcomm.server.configuration.Constant;
import com.vcomm.server.dto.login.LoginDTO;
import com.vcomm.server.userdetails.user.EmUserDetailsService;
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
@RequestMapping(value = {Constant.ADMIN_API_V1})
public class AdminAccessController {

    @Autowired
    EmUserDetailsService emUserDetailsService;

    @Autowired
    @Qualifier("emAuthenticationProvider")
    AuthenticationProvider authenticationProvider;

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void login(@Valid @RequestBody LoginDTO loginDTO) {

        UserDetails userDetails = emUserDetailsService.loadUserByUsername(loginDTO.getUsername());

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, loginDTO.getPassword(), userDetails.getAuthorities());
        Authentication auth = authenticationProvider.authenticate(usernamePasswordAuthenticationToken);

        SecurityContextHolder.getContext().setAuthentication(auth);
    }
}
