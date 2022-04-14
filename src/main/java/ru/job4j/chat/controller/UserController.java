package ru.job4j.chat.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.chat.domain.Message;
import ru.job4j.chat.domain.Room;
import ru.job4j.chat.domain.User;
import ru.job4j.chat.dto.UserDto;
import ru.job4j.chat.service.MessageService;
import ru.job4j.chat.service.RoomService;
import ru.job4j.chat.service.UserService;
import ru.job4j.chat.validator.RoomValidator;
import ru.job4j.chat.validator.UserValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class.getSimpleName());
    private final UserService users;
    private final RoomService rooms;
    private final MessageService messages;
    private final BCryptPasswordEncoder encoder;
    private final ObjectMapper objectMapper;
    private final UserValidator userValidator;
    private final RoomValidator roomValidator;

    public UserController(UserService users, RoomService rooms, MessageService messages, BCryptPasswordEncoder encoder, ObjectMapper objectMapper, UserValidator userValidator, RoomValidator roomValidator) {
        this.users = users;
        this.rooms = rooms;
        this.messages = messages;
        this.encoder = encoder;
        this.objectMapper = objectMapper;
        this.userValidator = userValidator;
        this.roomValidator = roomValidator;
    }

    @PostMapping("/sign-up")
    public void signUp(@RequestBody User user) {
        if (users.findByUsername(user.getUsername()) != null) {
            throw new IllegalArgumentException("Username is busy");
        }
        userValidator.validateNameAndPassword(user);
        user.setPassword(encoder.encode(user.getPassword()));
        users.save(user);
    }

    @GetMapping("/")
    public List<User> findAll() {
        return users.findAll();
    }

    @GetMapping("/{id}")
    public User findById(@PathVariable Long id) {
        var user = this.users.findById(id);
        return user.orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "User is not found"
        ));
    }

    @PutMapping("/")
    public ResponseEntity<Void> update(@RequestBody User user) {
        userValidator.validateNameAndPassword(user);
        this.users.save(user);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/")
    public ResponseEntity<Void> patch(@RequestBody UserDto userDto) {
        User user = users.findByUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        users.save(user);
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
        roomValidator.validate(room);
        User user = users.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "User is not found"
        ));
        room.getUsers().add(user);
        return new ResponseEntity<>(
                this.rooms.save(room),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}/rooms/")
    public ResponseEntity<Void> update(@RequestBody Room room) {
        roomValidator.validate(room);
        this.rooms.save(room);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/rooms/{rid}/")
    public ResponseEntity<Void> delete(@PathVariable Long id, @PathVariable Long rid) {
        Room room = new Room();
        room.setId(rid);
        this.rooms.delete(room);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/rooms/{rid}/message")
    public ResponseEntity<Message> createMessage(@PathVariable Long id, @PathVariable Long rid,
                                                 @RequestBody Message message) {
        User user = users.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "User is not found"
        ));
        Room room = rooms.findById(rid).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Room is not found"
        ));
        message.setUser(user);
        room.addMessage(message);
        rooms.save(room);
        return new ResponseEntity<>(
                this.messages.create(message),
                HttpStatus.CREATED
        );
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    public void exceptionHandler(Exception e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(new HashMap<>() {
            {
                put("message", e.getMessage());
                put("type", e.getClass());
            }
        }));
        LOGGER.error(e.getLocalizedMessage());
    }

}
