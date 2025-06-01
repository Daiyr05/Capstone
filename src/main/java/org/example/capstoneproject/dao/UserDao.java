package org.example.capstoneproject.dao;

import org.example.capstoneproject.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    List<User> selectUsers();
    Optional<User> selectUserById(Integer id);
    Integer insertUser(User user);
    Integer deleteUser(Integer id);

    Integer updateUser(User user);
}
