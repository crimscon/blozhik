package com.project.MVC.service;

import com.project.MVC.model.Comment;
import com.project.MVC.model.Message;
import com.project.MVC.model.User;
import com.project.MVC.model.UserProfile;
import com.project.MVC.model.enums.Gender;
import com.project.MVC.model.enums.Role;
import com.project.MVC.repository.CommentRepository;
import com.project.MVC.repository.MessagesRepository;
import com.project.MVC.repository.UserProfileRepository;
import com.project.MVC.repository.UserRepository;
import com.project.MVC.util.ThumbnailUtil;
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

    @Value("${upload.path}")
    private String uploadPath;

    public UserService(UserRepository userRepo, UserProfileRepository userProfileRepo, MessagesService messagesService, MessagesRepository messagesRepository, CommentRepository commentRepository, @Lazy PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.userProfileRepo = userProfileRepo;
        this.passwordEncoder = passwordEncoder;

        this.messagesService = messagesService;
        this.messagesRepository = messagesRepository;
        this.commentRepository = commentRepository;
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

    public User findById(Long aLong) {
        return userRepo.getOne(aLong);
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

    public void addUser(User user) {

        user.setActive(true);

        Set<Role> roleSet = new HashSet<>();

        if (findAll().isEmpty()) {
            roleSet.add(Role.ADMIN);
        }

        roleSet.add(Role.USER);
        user.setRoles(roleSet);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepo.save(user);
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
        if (!(deletingUser.getProfile_pic() == null || deletingUser.getProfile_pic().isEmpty())) {
            ThumbnailUtil.deleteIfExistFile(uploadPath, deletingUser.getProfile_pic());
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
                ThumbnailUtil.deleteIfExistFile(uploadPath, user.getProfile_pic());
                String filename = ThumbnailUtil.createFile(file, uploadPath, false);

                user.setProfile_pic(filename);
            }
        }

    }

    public void deletePicture(User currentUser, User user) throws IOException {
        if (currentUser.equals(user) || currentUser.getRoles().contains(Role.ADMIN)) {
            if (user.getProfile_pic() != null || !user.getProfile_pic().isEmpty()) {
                ThumbnailUtil.deleteIfExistFile(uploadPath, user.getProfile_pic());
                user.setProfile_pic(null);
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
