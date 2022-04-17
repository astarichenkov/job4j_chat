package ru.job4j.chat.controller;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.chat.domain.Message;
import ru.job4j.chat.domain.User;
import ru.job4j.chat.dto.MessageDto;
import ru.job4j.chat.service.MessageService;
import ru.job4j.chat.service.UserService;
import ru.job4j.chat.validator.Operation;

import javax.validation.Valid;

@RestController
@Validated
public class MessageController {

    private final MessageService messages;
    private final UserService users;

    public MessageController(MessageService messages, UserService users) {
        this.messages = messages;
        this.users = users;
    }

    @PatchMapping("/message")
    @Validated(Operation.OnUpdate.class)
    public Message patch(@Valid @RequestBody MessageDto messageDto) {
        var opt = messages.findById(messageDto.getId());
        Message message = opt.orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Message is not found"
        ));
        message.setMessage(messageDto.getName());
        User user = users.findById(messageDto.getUserId()).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "User is not found"
        ));
        message.setUser(user);
        messages.save(message);
        return message;
    }
}
