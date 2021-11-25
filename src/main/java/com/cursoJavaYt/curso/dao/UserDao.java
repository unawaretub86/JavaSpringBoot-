package com.cursoJavaYt.curso.dao;

import com.cursoJavaYt.curso.models.User;

import java.util.List;

public interface UserDao {
    List<User> read();

    void delete(Long id);

    void create(User user);

    User getUserByCredentials(User user);
}
