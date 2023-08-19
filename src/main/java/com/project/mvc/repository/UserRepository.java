package com.project.mvc.repository;

import com.project.mvc.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsernameIgnoreCase(String username);

    User findByEmailIgnoreCase(String email);

    @Query("select u from User u order by u.id")
    List<User> findAll();
}
