package com.project.mvc.repository;

import com.project.mvc.model.Comment;
import com.project.mvc.model.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findCommentsByMessage(Message message, Pageable pageable);

}
