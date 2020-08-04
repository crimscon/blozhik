package com.project.MVC.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;

public abstract class MessageUtil {
    public static Object convertDate(LocalDateTime date) {
        return Date.from(date.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static String createText(String text) {
        text = text.replaceAll("<", "&lt;");
        text = text.replaceAll(">", "&gt;");
        text = text.replaceAll("\n", "<br/>");

        return text;
    }

    public static String createAnnounce(String text) {
        String[] textArray = text.split(" ");
        if (textArray.length == 1 || text.length() <= 300) return text.substring(0, Math.min(text.length(), 300));

        StringBuilder stringBuilder = new StringBuilder();

        Arrays.stream(textArray).filter(s -> (stringBuilder.length() + s.length() + 3) <= 300)
                .forEach(s -> stringBuilder.append(s).append(" "));

        stringBuilder.trimToSize();
        stringBuilder.append("...");

        return stringBuilder.toString();
    }

}
