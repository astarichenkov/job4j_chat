package ru.job4j.chat.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String username;
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