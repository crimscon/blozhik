package com.project.MVC.service;

import com.project.MVC.model.User;
import com.project.MVC.model.enums.Role;
import com.project.MVC.repository.UserRepository;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    public void addUser() {
        User user = new User();

        user.setPassword(passwordEncoder.encode("somePassword"));
        user.setEmail("some@email.com");
        user.setUsername("someUsername");

        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("someUsername"));
        assertTrue(userService.addUser(user));

        Set<Role> roles = Arrays.stream(Role.values()).collect(Collectors.toSet());
        assertTrue(CoreMatchers.is(user.getRoles()).matches(roles));
        assertFalse("somePassword".equals(user.getPassword()));

        Mockito.verify(userRepository, Mockito.times(1)).save(user);
        Mockito.verify(userRepository, Mockito.times(1)).findByEmailIgnoreCase(user.getEmail());
    }

    @Test
    public void failAddUser() {
        User user = new User();

        user.setPassword(passwordEncoder.encode("somePassword"));
        user.setEmail("some@email.com");
        user.setUsername("someUsername");

        Mockito.doReturn(new User())
                .when(userRepository)
                .findByEmailIgnoreCase("some@email.com");

        assertFalse(userService.addUser(user));

        Mockito.verify(userRepository, Mockito.times(0)).save(user);
    }
}