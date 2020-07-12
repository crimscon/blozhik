package com.project.MVC.repository;

import com.project.MVC.model.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessagesRepository extends JpaRepository<Message, Long> {

    @Override
    Page<Message> findAll(Pageable pageable);

    @Override
    List<Message> findAll();
}
