package ru.job4j.chat.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.job4j.chat.validator.Operation;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @NotNull(message = "Id must be non null", groups = {
            Operation.OnUpdate.class, Operation.OnDelete.class})
    private Long id;

    @NotBlank(message = "Message must be not empty", groups = {
            Operation.OnUpdate.class, Operation.OnCreate.class})
    private String message;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
