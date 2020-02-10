package com.vcomm.server.userdetails.room;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
public class RoomPrincipal implements UserDetails {
  private RoomDAO roomDAO;

  public RoomPrincipal(RoomDAO roomDAO) {
    this.roomDAO = roomDAO;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return roomDAO.getAuthorities();
  }

  @Override
  public String getUsername() {
    return roomDAO.getRoomNumber();
  }

  @JsonIgnore
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  @Override
  public String getPassword() {
    return roomDAO.getPassword().getValue();
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
