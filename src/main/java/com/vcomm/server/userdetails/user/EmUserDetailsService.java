package com.vcomm.server.userdetails.user;

import com.vcomm.server.dto.user.UserDTO;
import com.vcomm.server.jpa.entity.Role;
import com.vcomm.server.jpa.entity.User;
import com.vcomm.server.jpa.respository.UserRepository;
import com.vcomm.server.service.db.RolesService;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EmUserDetailsService implements UserDetailsService {

  @Autowired
  UserRepository userRepository;

  private ModelMapper modelMapper = new ModelMapper();

  public EmUserDetailsService() {
    modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());

    modelMapper
      .addMappings(new PropertyMap<UserDTO, User>() {
        @Override
        protected void configure() {

        }
      })
      .addMappings(mapper -> mapper.skip(User::setRoles));
  }

  @Autowired
  PasswordEncoder passwordEncoder;

  @Autowired
  RolesService rolesService;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) {
    Optional<User> user = userRepository.findUserByUsername(username);
    if (!user.isPresent()) {
      throw new UsernameNotFoundException(username);
    }
    UserPrincipal principal = new UserPrincipal(new UserDAO(user.get()));
    principal.getAuthorities().size();
    return principal;
  }

  public List<User> findAll() {
    return userRepository.findAll();
  }

  public User findByUsername(String username) {
    return userRepository.findUserByUsername(username).orElseThrow(() -> new UnsupportedOperationException("no user found"));
  }

  public User findById(String id) {
    return userRepository.findById(UUID.fromString(id)).orElseThrow(() -> new UnsupportedOperationException("no user found"));
  }

  void updatePassword(UserDTO source, User destination) {
    if(source.getPassword() != null && !source.getPassword().trim().isEmpty()) {
      destination.setPassword(passwordEncoder.encode(source.getPassword()));
    }
  }


  public User save(UserDTO userDTO) {
    User user = modelMapper.map(userDTO, User.class);
    updatePassword(userDTO, user);
    userRepository.save(user);
    return user;
  }

  public User update(String id, UserDTO userDTO) {
    User user = findById(id);
    modelMapper.map(userDTO, user);
    updatePassword(userDTO, user);
    userRepository.save(user);
    return user;
  }

  public void delete(String id) {
    User user = findById(id);
    userRepository.delete(user);
  }

  public User addRole(String userID, String roleID) {
    User user = findById(userID);
    Role role = rolesService.findById(roleID);
    user.getRoles().add(role);
    userRepository.save(user);
    return user;
  }

  public User deleteRole(String userID, String roleID) {
    User user = findById(userID);
    Role searchRole = rolesService.findById(roleID);

    user
      .getRoles()
      .stream()
      .filter(role -> role.getId().equals(searchRole.getId()))
      .findFirst()
      .ifPresent(role -> user.getRoles().remove(role));

    userRepository.save(user);
    return user;
  }
}
