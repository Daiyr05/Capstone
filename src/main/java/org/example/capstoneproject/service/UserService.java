package org.example.capstoneproject.service;

import org.example.capstoneproject.entity.User;
import org.example.capstoneproject.exception.NotFoundException;
import org.example.capstoneproject.repository.UserRepository;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public int insertUser(User user){
        return userRepository.insertUser(user);
    }

    public List<User> selectUsers(){
        return userRepository.selectUsers();
    }

    public Optional<User> selectUserById(Integer id) {
        return userRepository.selectUserById(id);
    }

    public void deleteUser(Integer id){
        Optional<User> optionalUser =  userRepository.selectUserById(id);
        optionalUser.ifPresentOrElse(user -> {
            Integer result = userRepository.deleteUser(id);
            if (result != 1) {
                throw new IllegalStateException("oops could not delete user");
            }
        },() -> {
            throw new NotFoundException(String.format("Movie with id %s not found", id));
        });
    }

    public void updateUser(User user){
        Optional<User> optionalUser =  userRepository.selectUserById(user.getId());
        optionalUser.ifPresentOrElse(object -> {
            userRepository.updateUser(user);
        },() -> {
            throw new NotFoundException(String.format("Movie with id %s not found", user.getId()));
        });
    }

    public List<User> getAllClients() {
        return userRepository.selectClients();
    }
}
