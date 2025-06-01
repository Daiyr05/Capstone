package org.example.capstoneproject.repository;

import org.example.capstoneproject.dao.UserDao;
import org.example.capstoneproject.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public class UserRepositry implements UserDao {
    @Override
    public List<User> selectUsers() {
        return null;
    }

    @Override
    public Optional<User> selectUserById() {
        return Optional.empty();
    }

    @Override
    public Integer insertUser(User user) {
        return null;
    }

    @Override
    public Integer deleteUser(Integer id) {
        return null;
    }

    @Override
    public Integer updateUser(User user) {
        return null;
    }
}
