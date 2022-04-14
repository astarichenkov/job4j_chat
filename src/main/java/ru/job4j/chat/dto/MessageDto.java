package ru.job4j.chat.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageDto {
    private Long id;
    private String name;
    private String userId;
}
