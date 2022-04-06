package ru.job4j.chat.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.chat.Service.RoomService;
import ru.job4j.chat.Service.UserService;
import ru.job4j.chat.domain.Room;
import ru.job4j.chat.domain.User;

import java.util.List;


@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService users;
    private final RoomService rooms;

    public UserController(UserService users, RoomService rooms) {
        this.users = users;
        this.rooms = rooms;
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

    @DeleteMapping("/{id}")
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
}
