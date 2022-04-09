package ru.job4j.chat.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.chat.domain.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);

}
