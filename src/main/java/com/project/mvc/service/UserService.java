package com.project.mvc.service;

import com.project.mvc.model.Comment;
import com.project.mvc.model.Message;
import com.project.mvc.model.User;
import com.project.mvc.model.UserProfile;
import com.project.mvc.model.enums.Gender;
import com.project.mvc.model.enums.Role;
import com.project.mvc.repository.CommentRepository;
import com.project.mvc.repository.MessagesRepository;
import com.project.mvc.repository.UserProfileRepository;
import com.project.mvc.repository.UserRepository;
import com.project.mvc.util.ThumbnailUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepo;
    private final UserProfileRepository userProfileRepo;
    private final PasswordEncoder passwordEncoder;

    private final MessagesService messagesService;
    private final MessagesRepository messagesRepository;
    private final CommentRepository commentRepository;
    private final String uploadPath;

    public UserService(UserRepository userRepo,
                       UserProfileRepository userProfileRepo,
                       MessagesService messagesService,
                       MessagesRepository messagesRepository,
                       CommentRepository commentRepository,
                       @Lazy PasswordEncoder passwordEncoder,
                       @Value("${upload.path}") String uploadPath) {
        this.userRepo = userRepo;
        this.userProfileRepo = userProfileRepo;
        this.passwordEncoder = passwordEncoder;

        this.messagesService = messagesService;
        this.messagesRepository = messagesRepository;
        this.commentRepository = commentRepository;
        this.uploadPath = uploadPath;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsernameIgnoreCase(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

    public User getUserByUsername(String username) {
        return userRepo.findByUsernameIgnoreCase(username);
    }

    public User findById(Long id) {
        return userRepo.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public List<User> getUserList(String filter) {
        List<User> users;

        if (filter != null && !filter.isEmpty()) {
            users = new ArrayList<>();

            User user = (User) loadUserByUsername(filter);
            if (user != null) users.add(user);
        } else {
            users = userRepo.findAll();
        }

        return users;
    }

    public boolean addUser(User user) {

        if (userRepo.findByEmailIgnoreCase(user.getEmail()) != null) {
            return false;
        }

        user.setActive(true);

        Set<Role> roleSet = new HashSet<>();

        if (findAll().isEmpty()) {
            roleSet.add(Role.ADMIN);
        }

        roleSet.add(Role.USER);
        user.setRoles(roleSet);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepo.save(user);
        return true;

    }

    public List<User> findAll() {
        return userRepo.findAll();
    }

    public void deleteUser(User deletingUser, User currentUser) throws IOException {

        UserProfile userProfile = deletingUser.getUserProfile();
        Set<Message> messages = deletingUser.getMessages();
        Set<Comment> comments = deletingUser.getComments();
        List<Message> likes = messagesRepository.findAllWhereUserLike(deletingUser);

        comments.forEach(commentRepository::delete);
        likes.forEach(message -> {
            message.getLikes().remove(deletingUser);
            messagesRepository.save(message);
        });
        messages.forEach(message -> {
            try {
                messagesService.deleteMessage(message.getId(), currentUser);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        if (userProfile != null) {
            userProfileRepo.delete(userProfile);
        }
        if (!(deletingUser.getPicture() == null || deletingUser.getPicture().isEmpty())) {
            ThumbnailUtil.deleteIfExistFile(uploadPath, deletingUser.getPicture());
        }

        userRepo.delete(deletingUser);

    }

    public void subscribe(User channel, User currentUser) {
        channel.getSubscribers().add(currentUser);

        userRepo.save(channel);
    }

    public void unsubscribe(User channel, User currentUser) {
        channel.getSubscribers().remove(currentUser);

        userRepo.save(channel);
    }

    public void changeProfilePart(User currentUser, User user, Gender gender, String dateOfBirth) {
        if (currentUser.equals(user) || currentUser.getRoles().contains(Role.ADMIN)) {
            UserProfile userProfile = user.getUserProfile() == null ?
                    new UserProfile(user) : userProfileRepo.getOne(user.getUserProfile().getId());

            boolean dofChange = false,
                    genderChange = false;

            if (!userProfile.getGender().equals(gender)) {
                userProfile.setGender(gender);
                genderChange = true;
            }

            if (!dateOfBirth.equals("") && !dateOfBirth.isEmpty()) {
                String[] dateArr = dateOfBirth.split("-");

                LocalDateTime localDate = LocalDateTime.of(Integer.parseInt(dateArr[0]), Integer.parseInt(dateArr[1]), Integer.parseInt(dateArr[2]), 0, 0);

                if (!userProfile.getDateOfBirth().equals(localDate)) {
                    dofChange = true;
                    userProfile.setDateOfBirth(localDate);
                }
            }

            if (dofChange || genderChange) {
                userProfileRepo.save(userProfile);
                user.setUserProfile(userProfile);
                userRepo.save(user);
            }
        }
    }

    public void changeEmail(User currentUser, User user, String email) {
        if (currentUser.equals(user) || currentUser.getRoles().contains(Role.ADMIN)) {
            if (!user.getEmail().equals(email) || !email.isEmpty()) {
                user.setEmail(email);
                userRepo.save(user);
            }
        }
    }

    public void changePhone(User currentUser, User user, String phone) {
        if (currentUser.equals(user) || currentUser.getRoles().contains(Role.ADMIN)) {
            UserProfile userProfile = user.getUserProfile() == null ?
                    new UserProfile() : userProfileRepo.getOne(user.getUserProfile().getId());

            if (!phone.equals(userProfile.getPhoneNumber()) || !phone.isEmpty()) {
                userProfile.setPhoneNumber(phone);
                user.setUserProfile(userProfile);

                userProfileRepo.save(userProfile);
                userRepo.save(user);
            }
        }
    }

    public void editPicture(User currentUser, User user, MultipartFile file) throws IOException {
        if (currentUser.equals(user) || currentUser.getRoles().contains(Role.ADMIN)) {
            if (file != null && !file.getOriginalFilename().isEmpty()) {
                ThumbnailUtil.deleteIfExistFile(uploadPath, user.getPicture());
                String filename = ThumbnailUtil.createFile(file, uploadPath, false);

                user.setPicture(filename);
            }
        }

    }

    public void deletePicture(User currentUser, User user) throws IOException {
        if (currentUser.equals(user) || currentUser.getRoles().contains(Role.ADMIN)) {
            if (user.getPicture() != null || !user.getPicture().isEmpty()) {
                ThumbnailUtil.deleteIfExistFile(uploadPath, user.getPicture());
                user.setPicture(null);
                userRepo.save(user);
            }
        }
    }

    public void changePassword(User currentUser, User user,
                               String oldPassword, String newPassword, String newPasswordConfirm) {
        if (currentUser.getRoles().contains(Role.ADMIN)) {
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepo.save(user);
        } else if (currentUser.equals(user)) {
            if (passwordEncoder.matches(oldPassword, user.getPassword())
                    && newPassword.equals(newPasswordConfirm)) {
                user.setPassword(passwordEncoder.encode(newPassword));
                userRepo.save(user);
            }
        }
    }

    public void changeRoles(User currentUser, User user, Map<String, String> form) {
        if (currentUser.getRoles().contains(Role.ADMIN)) {

            Set<String> roles = Arrays.stream(Role.values())
                    .map(Role::name)
                    .collect(Collectors.toSet());

            user.getRoles().clear();

            for (String key : form.keySet()) {
                if (roles.contains(key)) {
                    user.getRoles().add(Role.valueOf(key));
                }
            }

            userRepo.save(user);
        }
    }
}
