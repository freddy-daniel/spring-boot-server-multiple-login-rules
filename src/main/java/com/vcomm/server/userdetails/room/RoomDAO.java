package com.vcomm.server.userdetails.room;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vcomm.server.jpa.entity.Privilege;
import com.vcomm.server.jpa.entity.Role;
import com.vcomm.server.jpa.entity.Room;
import com.vcomm.server.valueobject.MaskedString;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Data
public class RoomDAO implements Serializable {

    String id;

    Collection<? extends GrantedAuthority> authorities;
    String roomNumber;

    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    MaskedString password;

    String organisationId;
    boolean status;

    public RoomDAO(Room room) {
        authorities = getGrantedAuthorities(getPrivileges(room.getRoles()));
        this.id = room.getId().toString();
        this.roomNumber = room.getRoomNumber();
        this.password = new MaskedString(room.getPassword());
        this.organisationId = room.getOrganisationId();
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
