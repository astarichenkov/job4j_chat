package ru.job4j.chat.Service;

import org.springframework.stereotype.Service;
import ru.job4j.chat.domain.Room;
import ru.job4j.chat.repository.RoomRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {
    private final RoomRepository rooms;

    public RoomService(RoomRepository rooms) {
        this.rooms = rooms;
    }


    public List<Room> findAll() {
        return (List<Room>) rooms.findAll();
    }

    public Optional<Room> findById(Long id) {
        return rooms.findById(id);
    }

    public List<Room> findRoomsByUserId(Long id) {
        return rooms.findAllRoomsByUserId(id);
    }

    public Room save(Room room) {
        return rooms.save(room);
    }

    public void delete(Room room) {
        this.rooms.delete(room);
    }
}
