package ru.job4j.chat.service;

import org.springframework.stereotype.Service;
import ru.job4j.chat.domain.User;
import ru.job4j.chat.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository users;

    public UserService(UserRepository users) {
        this.users = users;
    }

    public List<User> findAll() {
        return (List<User>) users.findAll();
    }

    public Optional<User> findById(Long id) {
        return users.findById(id);
    }

    public User save(User user) {
        return users.save(user);
    }

    public void delete(User user) {
        users.delete(user);
    }

    public User findByUsername(String username) {
        return this.users.findByUsername(username);
    }
}
