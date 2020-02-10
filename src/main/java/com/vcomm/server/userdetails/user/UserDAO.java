package com.vcomm.server.userdetails.user;

import com.vcomm.server.jpa.entity.Privilege;
import com.vcomm.server.jpa.entity.Role;
import com.vcomm.server.jpa.entity.User;
import com.vcomm.server.valueobject.MaskedString;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vcomm.server.valueobject.MaskedString;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
public class UserDAO implements Serializable {

  String id;

  Collection<? extends GrantedAuthority> authorities;
  String username;

  @JsonIgnore
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  MaskedString password;

  String firstName;
  String lastName;
  String title;
  String profilePic;

  public UserDAO(User user) {
    authorities = getGrantedAuthorities(getPrivileges(user.getRoles()));
    this.id = user.getId().toString();
    this.username = user.getUsername();
    this.password = new MaskedString(user.getPassword());
    this.firstName = user.getFirstName();
    this.lastName = user.getLastName();
    this.title = user.getTitle();
    this.profilePic = user.getProfilePic();
  }

  private List<String> getPrivileges(Collection<Role> roles) {
    List<String> privileges = new ArrayList<>();
    List<Privilege> collection = new ArrayList<>();

    for (Role role : roles) {
      collection.addAll(role.getPrivileges());
    }

    for (Privilege item : collection) {
      privileges.add(item.getName());
    }
    return privileges;
  }

  private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
    List<GrantedAuthority> authorities = new ArrayList<>();
    for (String privilege : privileges) {
      authorities.add(new SimpleGrantedAuthority(privilege));
    }
    return authorities;
  }
}
