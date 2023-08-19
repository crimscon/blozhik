package com.project.mvc.controller;

import com.project.mvc.model.Message;
import com.project.mvc.model.User;
import com.project.mvc.model.dto.MessageDto;
import com.project.mvc.model.enums.Color;
import com.project.mvc.service.MessagesService;
import com.project.mvc.service.UserService;
import com.project.mvc.util.ControllerUtil;
import com.project.mvc.util.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@Controller
@SessionAttributes(names = {"errors", "errorMessage", "invalids", "invalidComment"})
@RequiredArgsConstructor
@RequestMapping("/messages/")
public class MessagesController {

    private final MessagesService messagesService;
    private final UserService userService;

    @GetMapping("/list")
    public String getMessages(HttpServletRequest request, HttpServletResponse response,
                              @CookieValue(name = "typeMessage", required = false, defaultValue = "all") String type,
                              Model model,
                              @AuthenticationPrincipal User user,
                              SessionStatus status,
                              @PageableDefault(sort = {"id"},
                                      direction = Sort.Direction.DESC) Pageable pageable) {

        model.addAttribute("page", messagesService.getMessages(request, response,
                (User) userService.loadUserByUsername(user.getUsername()), pageable));
        model.addAttribute("messageType", type);
        model.addAttribute("url", "/messages");

        ControllerUtil.addMessageSendPart(model);

        status.setComplete();

        return "main";
    }

    @GetMapping("/{id}")
    public String getMessage(@PathVariable Long id,
                             SessionStatus status,
                             @AuthenticationPrincipal User currentUser,
                             Model model,
                             @PageableDefault(sort = {"id"},
                                     direction = Sort.Direction.DESC) Pageable pageable) {
        MessageDto message = messagesService.findById(currentUser, id);
        messagesService.addViewers(message);

        model.addAttribute("message", message);
        model.addAttribute("convertedDateMessage", MessageUtil.convertDate(message.getDate()));
        model.addAttribute("author", userService.findById(message.getAuthor().getId()));
        model.addAttribute("comments", messagesService.findCommentsByMessage(messagesService.findById(id), pageable));
        model.addAttribute("url", "/messages/" + id);
        model.addAttribute("availableEdit",
                messagesService.availableEdit(message, currentUser));

        ControllerUtil.addMessageSendPart(model);

        status.setComplete();

        return "messageDetail";
    }

    @PostMapping("/feed")
    public String changeFeed(HttpServletRequest request, HttpServletResponse response) {
        messagesService.changeFeed(request, response);
        return "redirect:/messages/list";
    }

    @PostMapping("/")
    public String addMessage(RedirectAttributes redirectAttributes,
                             @RequestHeader(required = false) String referer,
                             @Valid Message message,
                             BindingResult bindingResult,
                             @RequestParam("picture") MultipartFile file,
                             @RequestParam Color color,
                             @AuthenticationPrincipal User user,
                             Model model) throws IOException {
        if (ControllerUtil.hasErrors(bindingResult, message, file)) {

            ControllerUtil.addErrorsToModel(bindingResult, message, file, model);

        } else {
            messagesService.createMessage(message, file, user, color);
        }

        return ControllerUtil.createRedirect(redirectAttributes, referer);

    }

    @GetMapping("/{id}/delete")
    public String deleteMessage(@PathVariable Long id,
                                @AuthenticationPrincipal User user) throws IOException {
        return messagesService.deleteMessage(id, user) ? "redirect:/messages/list" : "redirect:/messages/" + id;
    }

    @GetMapping("/{id}/edit")
    public String getEditForm(@PathVariable Long id,
                              @AuthenticationPrincipal User currentUser,
                              Model model) {
        MessageDto message = messagesService.findById(currentUser, id);

        model.addAttribute("message", message);

        ControllerUtil.addMessageSendPart(model);

        return messagesService.availableEdit(message, currentUser)
                ? "messageDetail" : "redirect:/messages/" + id;
    }

    @PostMapping("/{id}/edit")
    public String editMessage(@AuthenticationPrincipal User user,
                              @PathVariable Long id,
                              @RequestParam String title,
                              @RequestParam String text,
                              @RequestParam Color color) {
        messagesService.editMessage(user, id, title, text, color);

        return "redirect:/messages/" + id;
    }

    @GetMapping("/{id}/like")
    public String like(@AuthenticationPrincipal User user,
                       @PathVariable Long id,
                       RedirectAttributes redirectAttributes,
                       @RequestHeader(required = false) String referer) {
        messagesService.like(user, id);

        return ControllerUtil.createRedirect(redirectAttributes, referer);
    }

}
