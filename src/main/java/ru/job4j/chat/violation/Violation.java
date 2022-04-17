package ru.job4j.chat.violation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Violation {

    private final String fieldName;

    private final String message;

    public Violation(String fieldName, String message) {
        this.fieldName = fieldName;
        this.message = message;
    }
}
