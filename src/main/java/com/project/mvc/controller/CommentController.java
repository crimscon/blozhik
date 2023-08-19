package com.project.mvc.controller;

import com.project.mvc.model.Comment;
import com.project.mvc.model.User;
import com.project.mvc.service.MessagesService;
import com.project.mvc.util.ControllerUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.validation.Valid;
import java.util.Map;

@Controller
@SessionAttributes(names = {"invalids", "invalidComment"})
@RequiredArgsConstructor
public class CommentController {
    private final MessagesService messagesService;

    @PostMapping("messages/{messageId}/comment")
    public String addComment(@Valid Comment comment,
                             BindingResult bindingResult,
                             @PathVariable Long messageId,
                             @AuthenticationPrincipal User user,
                             Model model) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtil.getErrors(bindingResult);

            model.addAttribute("invalids", errors);
            model.addAttribute("invalidComment", comment);
        } else {
            messagesService.addComment(messageId, comment, user);
        }

        return "redirect:/messages/{messageId}/#commentBlock";
    }
}
