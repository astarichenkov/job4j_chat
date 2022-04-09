package ru.job4j.chat.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.job4j.chat.service.MessageService;
import ru.job4j.chat.service.RoomService;
import ru.job4j.chat.service.UserService;
import ru.job4j.chat.domain.Message;
import ru.job4j.chat.domain.Room;
import ru.job4j.chat.domain.User;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService users;
    private final RoomService rooms;
    private final MessageService messages;
    private final BCryptPasswordEncoder encoder;

    public UserController(UserService users, RoomService rooms, MessageService messages, BCryptPasswordEncoder encoder) {
        this.users = users;
        this.rooms = rooms;
        this.messages = messages;
        this.encoder = encoder;
    }

    @PostMapping("/sign-up")
    public void signUp(@RequestBody User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        users.save(user);
    }

    @GetMapping("/")
    public List<User> findAll() {
        return users.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id) {
        var employee = this.users.findById(id);
        return new ResponseEntity<>(
                employee.orElse(new User()),
                employee.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }

    @PostMapping("/create")
    public ResponseEntity<User> create(@RequestBody User user) {
        return new ResponseEntity<>(
                this.users.save(user),
                HttpStatus.CREATED
        );

    }

    @PutMapping("/")
    public ResponseEntity<Void> update(@RequestBody User user) {
        this.users.save(user);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        User user = new User();
        user.setId(id);
        this.users.delete(user);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/rooms")
    public List<Room> findRoomsByUserId(@PathVariable Long id) {
        return rooms.findRoomsByUserId(id);
    }

    @PostMapping("/{id}/rooms")
    public ResponseEntity<Room> create(@RequestBody Room room, @PathVariable Long id) {
        User user;
        Optional<User> opt = users.findById(id);
        if (opt.isEmpty()) {
            return new ResponseEntity<>(
                    HttpStatus.BAD_REQUEST
            );
        } else {
            user = opt.get();
            room.getUsers().add(user);
            return new ResponseEntity<>(
                    this.rooms.save(room),
                    HttpStatus.CREATED
            );
        }
    }

    @PutMapping("/{id}/rooms/")
    public ResponseEntity<Void> update(@RequestBody Room room) {
        this.rooms.save(room);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/rooms/{rid}/")
    public ResponseEntity<Void> delete(@PathVariable Long id, @PathVariable Long rid) {
        Room room = new Room();
        room.setId(id);
        this.rooms.delete(room);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/rooms/{rid}/message")
    public ResponseEntity<Message> createMessage(@PathVariable Long id, @PathVariable Long rid,
                                                 @RequestBody Message message) {
        User user = users.findById(id).get();
        Room room = rooms.findById(rid).get();
        message.setUser(user);
        room.addMessage(message);
        rooms.save(room);
        return new ResponseEntity<>(
                this.messages.create(message),
                HttpStatus.CREATED
        );
    }
}
