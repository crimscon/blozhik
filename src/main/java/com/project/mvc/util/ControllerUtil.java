package com.project.mvc.util;

import com.project.mvc.model.Message;
import com.project.mvc.model.User;
import com.project.mvc.model.enums.Color;
import lombok.experimental.UtilityClass;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.text.DecimalFormat;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@UtilityClass
public class ControllerUtil {
    public static Map<String, String> getErrors(BindingResult bindingResult) {
        Collector<FieldError, ?, Map<String, String>> collector = Collectors.toMap(
                fieldError -> fieldError.getField() + "Error",
                FieldError::getDefaultMessage
        );

        return bindingResult.getFieldErrors().stream().collect(collector);
    }

    private static boolean isTooBigFile(MultipartFile file) {
        return ((file != null || !file.isEmpty()) && ((file.getSize() / 1024) > 5000));
    }

    private static boolean isFillObject(Object object, MultipartFile file) {
        if (object instanceof Message) {
            return !(((Message) object).getText().isEmpty() && (file == null || file.isEmpty()));
        } else if (object instanceof User) {
            return false;
        } else return false;
    }

    public static boolean hasErrors(BindingResult bindingResult, Object object, MultipartFile file) {
        return bindingResult.hasErrors() || !isFillObject(object, file) || isTooBigFile(file);
    }

    public static void addErrorsToModel(BindingResult bindingResult, Message message, MultipartFile file, Model model) {

        Map<String, String> errors = ControllerUtil.getErrors(bindingResult);

        if (!isFillObject(message, file) && message.getTitle().isEmpty())
            errors.put("fillError", "Необходимо заполнить хотя бы одно поле");
        else if (!isFillObject(message, file) && !message.getTitle().isEmpty())
            errors.put("titleError", "Нельзя отправить сообщение только с заголовком");
        else if (isTooBigFile(file)) {
            double fileWeight = (double) file.getSize() / 1024;
            int degree = 1;
            while (fileWeight > 500) {
                fileWeight /= 1024;
                degree++;
            }
            errors.put("filenameError", "Нельзя добавлять файлы более 5Мб. " +
                    "Ваш файл весит " + new DecimalFormat("#0.0").format(fileWeight) + ((degree == 2) ? "Мб" : "Гб"));
        }

        errors.put("url", "add");

        model.addAttribute("errors", errors);
        model.addAttribute("errorMessage", message);

    }

    public static void addModelSubsPart(Model model, User channel, User currentUser) {
        if (channel.getSubscribers().contains(currentUser)) {
            model.addAttribute("meSubscribe", true);
        } else model.addAttribute("meSubscribe", false);

        model.addAttribute("subscribers", channel.getSubscribers().size());
        model.addAttribute("subscriptions", channel.getSubscriptions().size());
    }

    public static void addMessageSendPart(Model model) {
        model.addAttribute("messageSend", true);
        model.addAttribute("colors", Color.values());
    }

    public static String createRedirect(RedirectAttributes redirectAttributes, String referer) {
        UriComponents componentsBuilder = UriComponentsBuilder.fromHttpUrl(referer).build();

        componentsBuilder.getQueryParams().forEach(redirectAttributes::addAttribute);

        return "redirect:" + componentsBuilder.getPath();
    }
}
