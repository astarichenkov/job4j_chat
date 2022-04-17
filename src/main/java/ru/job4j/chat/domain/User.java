package ru.job4j.chat.domain;

import lombok.Getter;
import lombok.Setter;
import ru.job4j.chat.validator.Operation;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @NotNull(message = "Id must be non null", groups = {
            Operation.OnUpdate.class, Operation.OnDelete.class
    })
    private Long id;

    @NotBlank(message = "Name must be not empty", groups = {
            Operation.OnUpdate.class, Operation.OnCreate.class})
    private String username;

    @NotBlank(message = "Password must be not empty", groups = {
            Operation.OnUpdate.class, Operation.OnCreate.class})
    private String password;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "role_id")
    private Role role;

    public User() {
        this.role = Role.of(1L, "USER");
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.role = Role.of(1L, "USER");
    }
}