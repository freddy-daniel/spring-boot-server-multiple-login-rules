package com.vcomm.server.jpa.respository;

import com.vcomm.server.jpa.entity.Room;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RepositoryRestResource(path = "rooms")
public interface RoomRepository extends JpaRepository<Room,String> {
    @EntityGraph("Room-Graph")
    Optional<Room> findRoomByRoomNumber(@Param("room_number") String roomNumber);

    @EntityGraph("Room-Graph")
    Optional<Room> findById(@Param("id") UUID id);

    @EntityGraph("Room-Graph")
    @Override
    List<Room> findAll();
}
