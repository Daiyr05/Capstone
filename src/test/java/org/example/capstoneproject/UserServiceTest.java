package org.example.capstoneproject;

import org.example.capstoneproject.entity.User;
import org.example.capstoneproject.exception.NotFoundException;
import org.example.capstoneproject.repository.UserRepository;
import org.example.capstoneproject.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private UserRepository userRepository;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userService = new UserService(userRepository);
    }

    @Test
    void testInsertUser() {
        User user = new User();
        when(userRepository.insertUser(user)).thenReturn(1);

        int result = userService.insertUser(user);

        assertEquals(1, result);
        verify(userRepository).insertUser(user);
    }

    @Test
    void testSelectUsers() {
        List<User> users = Arrays.asList(new User(), new User());
        when(userRepository.selectUsers()).thenReturn(users);

        List<User> result = userService.selectUsers();

        assertEquals(2, result.size());
        verify(userRepository).selectUsers();
    }

    @Test
    void testSelectUserById() {
        User user = new User();
        when(userRepository.selectUserById(1)).thenReturn(Optional.of(user));

        Optional<User> result = userService.selectUserById(1);

        assertTrue(result.isPresent());
        assertEquals(user, result.get());
        verify(userRepository).selectUserById(1);
    }

    @Test
    void testDeleteUser_Success() {
        User user = new User();
        when(userRepository.selectUserById(1)).thenReturn(Optional.of(user));
        when(userRepository.deleteUser(1)).thenReturn(1);

        assertDoesNotThrow(() -> userService.deleteUser(1));

        verify(userRepository).selectUserById(1);
        verify(userRepository).deleteUser(1);
    }

    @Test
    void testDeleteUser_NotFound() {
        when(userRepository.selectUserById(1)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> userService.deleteUser(1));
        assertEquals("Movie with id 1 not found", exception.getMessage());

        verify(userRepository).selectUserById(1);
        verify(userRepository, never()).deleteUser(anyInt());
    }

    @Test
    void testDeleteUser_DeleteFails() {
        User user = new User();
        when(userRepository.selectUserById(1)).thenReturn(Optional.of(user));
        when(userRepository.deleteUser(1)).thenReturn(0);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> userService.deleteUser(1));
        assertEquals("oops could not delete user", exception.getMessage());

        verify(userRepository).selectUserById(1);
        verify(userRepository).deleteUser(1);
    }

    @Test
    void testUpdateUser_Success() {
        User user = new User();
        user.setId(1);
        when(userRepository.selectUserById(1)).thenReturn(Optional.of(user));

        assertDoesNotThrow(() -> userService.updateUser(user));

        verify(userRepository).selectUserById(1);
        verify(userRepository).updateUser(user);
    }

    @Test
    void testUpdateUser_NotFound() {
        User user = new User();
        user.setId(1);
        when(userRepository.selectUserById(1)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> userService.updateUser(user));
        assertEquals("Movie with id 1 not found", exception.getMessage());

        verify(userRepository).selectUserById(1);
        verify(userRepository, never()).updateUser(any());
    }

    @Test
    void testGetAllClients() {
        List<User> clients = Arrays.asList(new User(), new User());
        when(userRepository.selectClients()).thenReturn(clients);

        List<User> result = userService.getAllClients();

        assertEquals(2, result.size());
        verify(userRepository).selectClients();
    }

    @Test
    void testSelectUserByEmail() {
        User user = new User();
        when(userRepository.selectUserByEmail("test@example.com")).thenReturn(Optional.of(user));

        User result = userService.selectUserByEmail("test@example.com");

        assertEquals(user, result);
        verify(userRepository).selectUserByEmail("test@example.com");
    }
}
