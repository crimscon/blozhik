package com.project.MVC.service;

import com.project.MVC.model.User;
import com.project.MVC.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsernameIgnoreCase(username);
    }

    public <S extends User> void save(S s) {
        userRepo.save(s);
    }

    public User findById(Long aLong) {
        return userRepo.findById(aLong).get();
    }

    public List<User> findAll() {
        return userRepo.findAll();
    }

    public void deleteById(Long id) {
        userRepo.deleteById(id);
    }
}
