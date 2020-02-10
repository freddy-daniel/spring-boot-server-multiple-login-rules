package com.vcomm.server.userdetails.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
public class UserPrincipal implements UserDetails {
  private UserDAO userDAO;

  public UserPrincipal(UserDAO userDAO) {
    this.userDAO = userDAO;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return userDAO.getAuthorities();
  }



  @Override
  public String getUsername() {
    return userDAO.getUsername();
  }

  @JsonIgnore
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  @Override
  public String getPassword() {
    return userDAO.getPassword().getValue();
  }


  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

}
