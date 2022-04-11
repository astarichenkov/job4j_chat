package ru.job4j.chat.validator;

import org.springframework.stereotype.Component;
import ru.job4j.chat.domain.User;

@Component
public class UserValidator {

    public void validateNameAndPassword(User user) {
        var username = user.getUsername();
        var password = user.getPassword();
        if (username == null || username.equals("") || password == null || password.equals("")) {
            throw new NullPointerException("Username and password mustn't be empty");
        }
        if (password.length() < 6) {
            throw new IllegalArgumentException("Password must be >= 6 characters");
        }
    }

}
