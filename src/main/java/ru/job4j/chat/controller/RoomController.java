package ru.job4j.chat.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.job4j.chat.service.RoomService;
import ru.job4j.chat.domain.Message;
import ru.job4j.chat.domain.Room;

import java.util.List;

@RestController
public class RoomController {
    private final RoomService rooms;

    public RoomController(RoomService rooms) {
        this.rooms = rooms;
    }

    @GetMapping("/")
    public List<Room> fidAll() {
        return rooms.findAll();
    }

    @GetMapping("/rooms")
    public List<Room> findAll() {
        return rooms.findAll();
    }

    @GetMapping("/rooms/{id}")
    public ResponseEntity<Room> findById(@PathVariable Long id) {
        var room = rooms.findById(id);
        return new ResponseEntity<>(
                room.orElse(new Room()),
                room.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }

    @GetMapping("/rooms/{id}/message")
    public List<Message> fidAll(@PathVariable Long id) {
        Room room = rooms.findById(id).get();
        return room.getMessages();
    }
}


