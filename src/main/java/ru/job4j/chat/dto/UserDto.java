package ru.job4j.chat.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UserDto {
    @NotBlank(message = "Username must be not empty")
    private String username;

    @Size(min = 6, message = "Password must be not empty and >= 6 characters")
    private String password;
}
