package com.example.emergencyapp.repositories;

import com.example.emergencyapp.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class UserRepositoryTest {

    @Mock
    private UserRepository userRepository;

    private User user;
    private List<User> userList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setRole("Admin");

        userList = new ArrayList<>();
        userList.add(user);
    }

    @Test
    void testSaveUser() {
        when(userRepository.save(user)).thenAnswer(invocation -> {
            User savedUser = invocation.getArgument(0);
            userList.add(savedUser);
            return savedUser;
        });

        User savedUser = userRepository.save(user);

        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isEqualTo(1L);
        assertThat(savedUser.getFirstName()).isEqualTo("John");
        assertThat(savedUser.getLastName()).isEqualTo("Doe");
        assertThat(savedUser.getRole()).isEqualTo("Admin");

        assertThat(userList).contains(savedUser);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testFindById() {
        when(userRepository.findById(1L)).thenAnswer(invocation -> {
            Long id = invocation.getArgument(0);
            return userList.stream().filter(u -> u.getId().equals(id)).findFirst();
        });

        Optional<User> foundUser = userRepository.findById(1L);

        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getId()).isEqualTo(1L);
        assertThat(foundUser.get().getFirstName()).isEqualTo("John");
        assertThat(foundUser.get().getLastName()).isEqualTo("Doe");
        assertThat(foundUser.get().getRole()).isEqualTo("Admin");

        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testDeleteUser() {
        doAnswer(invocation -> {
            User userToDelete = invocation.getArgument(0);
            userList.removeIf(u -> u.equals(userToDelete));
            return null;
        }).when(userRepository).delete(user);

        userRepository.delete(user);

        assertThat(userList).doesNotContain(user);
        verify(userRepository, times(1)).delete(user);
    }
}
