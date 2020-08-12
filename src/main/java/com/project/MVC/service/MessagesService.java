package com.project.MVC.service;

import com.project.MVC.model.Comment;
import com.project.MVC.model.Message;
import com.project.MVC.model.User;
import com.project.MVC.model.dto.MessageDto;
import com.project.MVC.model.enums.Color;
import com.project.MVC.model.enums.Role;
import com.project.MVC.repository.CommentRepository;
import com.project.MVC.repository.MessagesRepository;
import com.project.MVC.util.MessageUtil;
import com.project.MVC.util.ThumbnailUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class MessagesService {

    private final MessagesRepository messagesRepository;
    private final CommentRepository commentRepository;
    private final UserService userService;

    @Value("${upload.path}")
    private String uploadPath;

    public MessagesService(MessagesRepository messagesRepository,
                           CommentRepository commentRepository,
                           @Lazy UserService userService) {
        this.messagesRepository = messagesRepository;
        this.commentRepository = commentRepository;
        this.userService = userService;
    }

    public void createMessage(Message message,
                              MultipartFile file,
                              User user,
                              Color color) throws IOException {
        message.setText(MessageUtil.createText(message.getText()));
        message.setAuthor(user);
        message.setColor(color);
        message.setAnnounce(MessageUtil.createAnnounce(message.getText()));
        message.setDate(LocalDateTime.now());

        if (file != null && !file.getOriginalFilename().isEmpty()) {
            String filename = ThumbnailUtil.createFile(file, uploadPath, true);
            message.setFilename(filename);
        }

        if (message.getFilename() != null ||
                !message.getTitle().isEmpty() ||
                !message.getText().isEmpty()) {
            messagesRepository.save(message);
        } else ThumbnailUtil.deleteIfExistFile(uploadPath, message.getFilename());
    }

    public Page<MessageDto> findWhereMeLiked(Pageable pageable, User user) {
        return messagesRepository.findWhereMeLiked(pageable, user);
    }

    public MessageDto findById(User user, Long id) {
        return messagesRepository.findById(id, user);
    }

    public Message findById(Long id) {
        return messagesRepository.getOne(id);
    }

    public boolean deleteMessage(Long id, User currentUser) throws IOException {
        MessageDto message = findById(currentUser, id);

        if (currentUser.getId().equals(message.getAuthor().getId()) || currentUser.getRoles().contains(Role.ADMIN)) {
            if (message.getFilename() != null) {
                ThumbnailUtil.deleteIfExistFile(uploadPath, message.getFilename());
            }
            Set<Comment> comments = findById(id).getComments();
            comments.forEach(commentRepository::delete);
            messagesRepository.delete(messagesRepository.getOne(message.getId()));

            return true;
        }
        return false;
    }

    public boolean availableEdit(MessageDto message, User currentUser) {
        return (message.getDate() != null
                && message.getAuthor().getId().equals(currentUser.getId())
                && message.getDate().plusDays(1).isAfter(LocalDateTime.now()))
                || currentUser.getRoles().contains(Role.ADMIN);
    }

    public void editMessage(User user, Long id, String title, String text, Color color) {
        Message message = messagesRepository.getOne(id);

        boolean changeText = false,
                changeTitle = false,
                changeColor = false;

        text = MessageUtil.createText(text);

        if (!text.isEmpty() && !message.getText().equals(text)) {

            message.setText(text);
            message.setAnnounce(MessageUtil.createAnnounce(text));
            changeText = true;
        }

        if (!title.isEmpty() && !message.getTitle().equals(title)) {
            message.setTitle(title);
            changeTitle = true;
        }

        if (!message.getColor().equals(color)) {
            message.setColor(color);
            changeColor = true;
        }

        if (changeColor || changeText || changeTitle)
            messagesRepository.save(message);
    }

    public Page<MessageDto> messageListForUser(Pageable pageable, User author, User currentUser) {
        return messagesRepository.findByUser(pageable, author, currentUser);
    }

    public Page<MessageDto> findAll(Pageable pageable, User user) {
        return messagesRepository.findAll(pageable, user);
    }

    public Page<MessageDto> findSubsMessages(User user, Pageable pageable) {
        Set<User> subs = new HashSet<>(user.getSubscriptions());
        subs.add(user);
        return messagesRepository.findSubsMessages(subs, user, pageable);
    }

    public void addViewers(MessageDto messageDto) {
        Message message = messagesRepository.getOne(messageDto.getId());

        long viewers = message.getViewers() + 1;
        message.setViewers(viewers);

        messagesRepository.save(message);
    }

    public void like(User user, Long id) {
        Message message = findById(id);
        Set<User> likes = message.getLikes();

        if (!likes.remove(user)) likes.add(user);

        messagesRepository.save(message);
    }

    public void addComment(Long messageId, Comment comment, User user) {
        Message message = findById(messageId);
        comment.setText(MessageUtil.createText(comment.getText()));
        comment.setUser(user);
        comment.setMessage(message);
        commentRepository.save(comment);
    }

    public Page<Comment> findCommentsByMessage(Message message, Pageable pageable) {
        return commentRepository.findCommentsByMessage(message, pageable);
    }

    public Integer findUserMessageCount(User user) {
        return messagesRepository.findCountMessagesByUser(user);
    }

    public Integer findUserLikes(User user) {
        return messagesRepository.findAllWhereUserLike(user).size();
    }

    public Page<MessageDto> getMessages(HttpServletRequest request, HttpServletResponse response, User user, Pageable pageable) {
        response.setHeader("Cookie", "Path=/");

        AtomicBoolean typeFounded = new AtomicBoolean(false);
        Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName()
                        .equals("typeMessage"))
                .forEach(cookie -> typeFounded.set(true));

        if (!typeFounded.get()) {
            Cookie cookie = new Cookie("typeMessage",
                    ((User) userService.loadUserByUsername(user.getUsername())).
                            getSubscriptions().isEmpty() ? "all" : "subs");
            cookie.setMaxAge(Integer.MAX_VALUE);
            response.addCookie(cookie);
        }

        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies)
            if ("typeMessage".equals(cookie.getName())) {
                return "all".equals(cookie.getValue()) ? findAll(pageable, user) : findSubsMessages(user, pageable);
            }

        return findAll(pageable, user);
    }

    public void changeFeed(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if ("typeMessage".equals(cookie.getName())) {
                cookie.setValue(cookie.getValue().equals("all") ? "subs" : "all");
                cookie.setMaxAge(Integer.MAX_VALUE);
                response.addCookie(cookie);
            }
        }
    }
}
