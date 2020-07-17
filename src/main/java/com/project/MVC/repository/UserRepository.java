package com.project.MVC.repository;

import com.project.MVC.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsernameIgnoreCase(String username);

    @Query(value = "select * from usr order by id", nativeQuery = true)
    List<User> findAll();
}
