package ru.job4j.chat.dto;

import lombok.Getter;
import lombok.Setter;
import ru.job4j.chat.validator.Operation;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class RoomDto {
    @Min(value = 1, message = "Id must be non null", groups = {
            Operation.OnUpdate.class})
    private Long id;

    @NotBlank(message = "Name must be not empty", groups = {
            Operation.OnUpdate.class, Operation.OnCreate.class})
    private String name;
}
