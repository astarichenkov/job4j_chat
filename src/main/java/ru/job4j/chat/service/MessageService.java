package ru.job4j.chat.service;

import org.springframework.stereotype.Service;
import ru.job4j.chat.domain.Message;
import ru.job4j.chat.repository.MessageRepository;

import java.util.Optional;

@Service
public class MessageService {
    private final MessageRepository messages;

    public MessageService(MessageRepository messages) {
        this.messages = messages;
    }

    public Message create(Message message) {
        return this.messages.save(message);
    }

    public Optional<Message> findById(Long id) {
        return this.messages.findById(id);
    }

    public void save(Message message) {
        this.messages.save(message);
    }
}
