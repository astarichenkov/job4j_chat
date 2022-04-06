package ru.job4j.chat.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.job4j.chat.domain.Room;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface RoomRepository extends CrudRepository<Room, Long> {

    @Transactional
    @Modifying
    @Query("select distinct r from Room r " +
            "join fetch r.users u " +
            "where u.id = :id")
    List<Room> findAllRoomsByUserId(@Param("id") Long id);
}
