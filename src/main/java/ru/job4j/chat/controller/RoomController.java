package ru.job4j.chat.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.chat.domain.Message;
import ru.job4j.chat.domain.Room;
import ru.job4j.chat.dto.RoomDto;
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

    @PatchMapping("/")
    public Room patch(@RequestBody RoomDto roomDto) {
        var opt = rooms.findById(roomDto.getId());
        Room room = opt.orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Room is not found"
        ));
        room.setName(roomDto.getName());
        rooms.save(room);
        return room;
    }
}


