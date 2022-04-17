package ru.job4j.chat.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.job4j.chat.validator.Operation;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @Min(value = 1, message = "Id must be non null", groups = {
            Operation.OnUpdate.class, Operation.OnDelete.class})
    private Long id;

    @NotBlank(message = "Name must be not empty", groups = {
            Operation.OnUpdate.class, Operation.OnCreate.class})
    private String name;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<User> users = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> messages = new ArrayList<>();

    public void addMessage(Message message) {
        this.messages.add(message);
    }
}
