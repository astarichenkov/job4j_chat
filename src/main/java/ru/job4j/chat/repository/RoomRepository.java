package ru.job4j.chat.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.job4j.chat.domain.Room;

import java.util.List;

@Repository
public interface RoomRepository extends CrudRepository<Room, Long> {

    @Query("select distinct r from Room r "
            + "join fetch r.users u "
            + "where u.id = :id")
    List<Room> findAllRoomsByUserId(@Param("id") Long id);

    @Query("select r from Room r "
            + "inner join r.users users "
            + "where users.id = ?1")
    List<Room> findAllRoomsByUserIdGeneratedByIdea(Long id);

    List<Room> findByUsersIdIs(Long id);

}
