package com.vcomm.server.userdetails.room;

import com.vcomm.server.dto.room.RoomDTO;
import com.vcomm.server.jpa.entity.Role;
import com.vcomm.server.jpa.entity.Room;
import com.vcomm.server.jpa.respository.RoomRepository;
import com.vcomm.server.service.db.RolesService;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
public class RoomUserDetailsService implements UserDetailsService {

  @Autowired
  RoomRepository roomRepository;

  private ModelMapper modelMapper = new ModelMapper();

  public RoomUserDetailsService() {
    modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());

    modelMapper
      .addMappings(new PropertyMap<RoomDTO, Room>() {
        @Override
        protected void configure() {

        }
      })
      .addMappings(mapper -> mapper.skip(Room::setRoles));
  }

  @Autowired
  @Qualifier("roomPasswordEncoder")
  PasswordEncoder passwordEncoder;

  @Autowired
  RolesService rolesService;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) {
    Optional<Room> room = roomRepository.findRoomByRoomNumber(username);
    if (!room.isPresent()) {
      throw new UsernameNotFoundException(username);
    }
    RoomPrincipal principal = new RoomPrincipal(new RoomDAO(room.get()));
    principal.getAuthorities().size();
    return principal;
  }

  public List<Room> findAll() {
    return roomRepository.findAll();
  }

  public Room findByUsername(String username) {
    return roomRepository.findRoomByRoomNumber(username).orElseThrow(() -> new UnsupportedOperationException("no user found"));
  }

  public Room findById(String id) {
    return roomRepository.findById(UUID.fromString(id)).orElseThrow(() -> new UnsupportedOperationException("no user found"));
  }

  void updatePassword(RoomDTO source, Room destination) {
    if(source.getPassword() != null && !source.getPassword().trim().isEmpty()) {
      destination.setPassword(passwordEncoder.encode(source.getPassword()));
    }
  }

  public Room save(RoomDTO roomDTO) {
    Room room = modelMapper.map(roomDTO, Room.class);
    updatePassword(roomDTO, room);
    roomRepository.save(room);
    return room;
  }

  public Room update(String id, RoomDTO roomDTO) {
    Room room = findById(id);
    modelMapper.map(roomDTO, room);
    updatePassword(roomDTO, room);
    roomRepository.save(room);
    return room;
  }

  public void delete(String id) {
    Room room = findById(id);
    roomRepository.delete(room);
  }

  public Room addRole(String roomID, String roleID) {
    Room room = findById(roomID);
    Role role = rolesService.findById(roleID);
    room.getRoles().add(role);
    roomRepository.save(room);
    return room;
  }

  public Room deleteRole(String roomID, String roleID) {
    Room room = findById(roomID);
    Role searchRole = rolesService.findById(roleID);

    room
      .getRoles()
      .stream()
      .filter(role -> role.getId().equals(searchRole.getId()))
      .findFirst()
      .ifPresent(role -> room.getRoles().remove(role));

    roomRepository.save(room);
    return room;
  }
}
