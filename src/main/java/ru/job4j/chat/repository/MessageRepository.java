package ru.job4j.chat.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.job4j.chat.domain.Message;
import ru.job4j.chat.domain.Room;

import java.util.List;

@Repository
public interface MessageRepository extends CrudRepository<Message, Long> {

//    @Query("select distinct r from Room r " +
//            "join fetch r.users u " +
//            "where u.id = :id")
//    List<Message> findAllMessagesByRoomId(@Param("id") Long id);
//
//    List<Message> find(String city);

}
