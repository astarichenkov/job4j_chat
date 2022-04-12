package ru.job4j.chat.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.chat.domain.Message;
import ru.job4j.chat.domain.Room;
import ru.job4j.chat.service.RoomService;

import java.util.List;

@RestController
@RequestMapping("/rooms")
public class RoomController {
    private final RoomService rooms;

    public RoomController(RoomService rooms) {
        this.rooms = rooms;
    }

    @GetMapping("/")
    public ResponseEntity<List<Room>> findAll() {
        List<Room> list = rooms.findAll();
        return list.isEmpty() ? ResponseEntity.status(HttpStatus.NOT_FOUND).build()
                : new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public Room findById(@PathVariable Long id) {
        return rooms.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Room is not found"
        ));
    }

    @GetMapping("/{id}/message")
    public List<Message> fidAll(@PathVariable Long id) {
        Room room = rooms.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Room is not found"
        ));
        return room.getMessages();
    }
}


