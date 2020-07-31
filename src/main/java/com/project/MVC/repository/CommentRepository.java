package com.project.MVC.repository;

import com.project.MVC.model.Comment;
import com.project.MVC.model.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findCommentsByMessage(Message message, Pageable pageable);

}
