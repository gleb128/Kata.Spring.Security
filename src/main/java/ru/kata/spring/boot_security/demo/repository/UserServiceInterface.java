package ru.kata.spring.boot_security.demo.repository;

import ru.kata.spring.boot_security.demo.entity.User;

import java.util.List;

public interface UserInterface {
    void updateUser(User user, List<Long> roleIds);
}
