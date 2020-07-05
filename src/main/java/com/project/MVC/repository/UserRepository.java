package com.project.MVC.repository;

import com.project.MVC.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsernameIgnoreCase(String username);
}
