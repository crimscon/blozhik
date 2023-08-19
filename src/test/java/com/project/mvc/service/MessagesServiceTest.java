package com.project.mvc.service;

import com.project.mvc.model.Comment;
import com.project.mvc.model.Message;
import com.project.mvc.model.User;
import com.project.mvc.model.dto.MessageDto;
import com.project.mvc.model.enums.Color;
import com.project.mvc.model.enums.Role;
import com.project.mvc.repository.CommentRepository;
import com.project.mvc.repository.MessagesRepository;
import com.project.mvc.util.MessageUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class MessagesServiceTest {

    @MockBean
    MessagesRepository messagesRepository;
    @MockBean
    CommentRepository commentRepository;
    @MockBean
    UserService userService;
    @Autowired
    private MessagesService messagesService;

    @Test
    void createMessageFailedPic() {
        Message message = new Message();
        message.setTitle("someTitle");
        message.setText("someText");
        MultipartFile file = new MockMultipartFile("somePic", "somePic", null, new byte[100]);

        assertThrows(IOException.class, () -> messagesService.createMessage(message, file, new User(), Color.PRIMARY));

        Mockito.verify(messagesRepository, Mockito.times(0)).save(ArgumentMatchers.any(Message.class));
    }

    @Test
    void createMessage() throws IOException {
        Message message = new Message();
        message.setTitle("");
        message.setText("");

        assertFalse(messagesService.createMessage(message, null, new User(), Color.PRIMARY));

        message.setText("someText");
        assertTrue(messagesService.createMessage(message, null, new User(), Color.PRIMARY));
    }

    @Test
    void deleteMessage() throws IOException {
        Message message = createMessageToTest();
        addCommentsToTest(message);

        MessageDto messageDto = new MessageDto(message, (long) message.getLikes().size(), false);

        Mockito.doReturn(messageDto)
                .when(messagesRepository)
                .findById(message.getId(), message.getAuthor());

        Mockito.doReturn(message).
                when(messagesRepository)
                .getOne(message.getId());

        assertTrue(messagesService.deleteMessage(message.getId(), message.getAuthor()));

        Mockito.verify(messagesRepository, Mockito.times(1)).delete(ArgumentMatchers.any(Message.class));
        Mockito.verify(commentRepository, Mockito.times(message.getComments().size()))
                .delete(ArgumentMatchers.any(Comment.class));
    }

    @Test
    void createComment() {
        Message message = createMessageToTest();
        Comment comment = createCommentToTest(message, "text");

        assertFalse(messagesService.addComment(message.getId(), comment, new User()));

        Mockito.doReturn(message)
                .when(messagesRepository)
                .getOne(message.getId());

        assertTrue(messagesService.addComment(message.getId(), comment, new User()));

    }

    @Test
    void like() {
        Message message = createMessageToTest();
        User user = new User();
        user.setId(2L);

        message.getLikes().add(user);

        Mockito.doReturn(message)
                .when(messagesRepository)
                .getOne(ArgumentMatchers.anyLong());

        assertFalse(messagesService.like(user, message.getId()));
        assertTrue(messagesService.like(user, message.getId()));
    }

    @Test
    void availableEdit() {
        MessageDto message = new MessageDto(createMessageToTest(), 0L, false);

        assertTrue(messagesService.availableEdit(message, message.getAuthor()));

        User user = createUserToTest(10L);
        assertFalse(messagesService.availableEdit(message, user));

        user.getRoles().add(Role.ADMIN);
        assertTrue(messagesService.availableEdit(message, user));
    }

    @Test
    void editMessage() {
        User user = createUserToTest(10L);
        Message message = createMessageToTest();

        Mockito.doReturn(message)
                .when(messagesRepository)
                .getOne(message.getId());

        assertFalse(messagesService.editMessage(user, message.getId(),
                message.getTitle(), message.getText(), message.getColor()));

        Mockito.verify(messagesRepository, Mockito.times(1)).getOne(ArgumentMatchers.anyLong());

        assertTrue(messagesService.editMessage(user, message.getId(),
                message.getTitle(), message.getText(), Color.DANGER));

        Mockito.verify(messagesRepository, Mockito.times(1)).save(ArgumentMatchers.any(Message.class));
    }


    private Message createMessageToTest() {
        User user = createUserToTest(5L);

        Message message = new Message();

        message.setColor(Color.PRIMARY);
        message.setId(1L);
        message.setAuthor(user);
        message.setText("someText");
        message.setAnnounce(MessageUtil.createAnnounce(message.getText()));
        message.setDate(LocalDateTime.now());
        message.setTitle("someTitle");

        return message;
    }

    private User createUserToTest(Long id) {
        User user = new User();

        user.setId(id);
        user.setUsername(id.toString());
        user.setEmail(id + "@mail.com");

        Set<Role> roles = new HashSet<>();
        roles.add(Role.USER);

        user.setRoles(roles);

        return user;
    }

    private void addCommentsToTest(Message message) {
        Set<Comment> comments = message.getComments();
        for (int i = 0; i < 10; i++) {
            comments.add(createCommentToTest(message, String.valueOf(i)));
        }
    }

    private Comment createCommentToTest(Message message, String text) {
        Comment comment = new Comment();
        comment.setDate(LocalDateTime.now());
        comment.setMessage(message);
        comment.setText(text + "-text");
        comment.setId(Long.parseLong(String.valueOf(new Random().nextInt())));
        comment.setUser(new User());

        return comment;
    }
}