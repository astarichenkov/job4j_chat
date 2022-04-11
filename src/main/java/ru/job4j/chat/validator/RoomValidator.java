package ru.job4j.chat.validator;

import org.springframework.stereotype.Component;
import ru.job4j.chat.domain.Room;

@Component
public class RoomValidator {

    public void validate(Room room) {
        var name = room.getName();
        if (name == null || name.equals("")) {
            throw new NullPointerException("Room name mustn't be empty");
        }
    }

}
