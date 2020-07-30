package com.project.MVC.util;

import com.project.MVC.model.Message;
import com.project.MVC.model.User;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.multipart.MultipartFile;

import java.text.DecimalFormat;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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

    private static boolean isFillObject(Object object) {
        if (object instanceof Message) {
            return ((Message) object).getText().isEmpty();
        } else if (object instanceof User) {
            return false;
        } else return false;
    }

    public static boolean hasErrors(BindingResult bindingResult, Object object, MultipartFile file) {

        return bindingResult.hasErrors() || isFillObject(object) || isTooBigFile(file);
    }

    public static void addErrorsToModel(BindingResult bindingResult, Message message, MultipartFile file, Model model) {

        Map<String, String> errors = ControllerUtil.getErrors(bindingResult);

        if (isFillObject(message) && message.getTitle().isEmpty())
            errors.put("fillError", "Необходимо заполнить хотя бы одно поле");
        else if (isFillObject(message) && !message.getTitle().isEmpty())
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
}
