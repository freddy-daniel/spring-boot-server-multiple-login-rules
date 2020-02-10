package com.vcomm.server.controller.admin;

import com.vcomm.server.configuration.Constant;
import com.vcomm.server.dto.user.UserDTO;
import com.vcomm.server.jpa.entity.User;
import com.vcomm.server.userdetails.user.EmUserDetailsService;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = {Constant.ADMIN_API_V1})
public class UserController {

  private ModelMapper modelMapper = new ModelMapper();

  public UserController() {

    modelMapper
      .addMappings(new PropertyMap<User, UserDTO>() {
        @Override
        protected void configure() {

        }
      });

  }

  @Autowired
  EmUserDetailsService emUserDetailsService;

  @RequestMapping(value = "/me",  method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public UserDTO me(final Principal principal) {
    return this.convertToDto(emUserDetailsService.findByUsername(principal.getName()));
  }

  @RequestMapping(value = "/users",  method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public List<UserDTO> getUsers() {
    return
      emUserDetailsService
      .findAll()
      .stream()
      .map(this::convertToDto)
      .collect(Collectors.toList());
  }

  @RequestMapping(value = "/user/{id}",  method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public UserDTO getUser(@PathVariable String id) {
    return
      convertToDto(
      emUserDetailsService
      .findById(id));
  }

  @RequestMapping(value = "/user",  method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  @ResponseBody
  public UserDTO createUser(@Valid @RequestBody UserDTO userDTO) {
    return this.convertToDto(emUserDetailsService.save(userDTO));
  }

  @RequestMapping(value = "/user/{id}",  method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public UserDTO updateUser(@PathVariable String id, @Valid @RequestBody UserDTO userDTO) {
    userDTO.setUsername(null); // Do not allow updating username, only creating
    return this.convertToDto(emUserDetailsService.update(id, userDTO));
  }

  @RequestMapping(value = "/user/{id}",  method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteUser(@PathVariable String id) {
    emUserDetailsService.delete(id);
  }

  @RequestMapping(value = "/user/{userID}/role/{roleID}",  method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public UserDTO addRole(@PathVariable String userID, @PathVariable String roleID) {
    return this.convertToDto(
      emUserDetailsService.addRole(userID, roleID)
    );
  }

  @RequestMapping(value = "/user/{userID}/role/{roleID}",  method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public UserDTO deleteRole(@PathVariable String userID, @PathVariable String roleID) {
    return this.convertToDto(
      emUserDetailsService.deleteRole(userID, roleID)
    );
  }

  private UserDTO convertToDto(User user) {
    return modelMapper.map(user, UserDTO.class);
  }
}
