package org.bassani.examplemodellib.util;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class MessageBundle {

    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("error_messages", new Locale("pt"));
    private static final String NOT_FOUND_MESSAGE = "Mensagem não encontrada no ResourceBundle";

    private MessageBundle(){}

    public static String getMessage(String key) {
        if (RESOURCE_BUNDLE.containsKey(key)) {
            return RESOURCE_BUNDLE.getString(key);
        }
        return NOT_FOUND_MESSAGE;
    }

    public static String getMessage(String key, Object... args) {
        if (RESOURCE_BUNDLE.containsKey(key)) {
            String pattern = RESOURCE_BUNDLE.getString(key);
            return MessageFormat.format(pattern, args);
        }
        return NOT_FOUND_MESSAGE;
    }
}
