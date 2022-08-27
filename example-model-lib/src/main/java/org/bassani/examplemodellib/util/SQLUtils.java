package org.bassani.examplemodellib.util;

import static org.springframework.util.Assert.hasText;

public class SQLUtils {

    public static final String FUNCTION_TO_CHAR = "TO_CHAR";
    public static final String FUNCTION_NLS_NUMERIC = "nls_numeric_characters=',.'";
    public static final String FUNCTION_TRUNC = "TRUNC";

    public static final String FORMAT_DATE = "DD/MM/YYYY HH24:MI:SS";
    public static final String FORMAT_MONEY = "FM999G999G999D90";

    public static String like(final String valueInit) {

        String value;

        if (valueInit.contains("%")) {
            value = valueInit.replace("%", "").trim();
            if (!value.isEmpty()) {
                return startWith(value);
            }

        } else {
            value = valueInit;
        }

        if (value.isEmpty()) {
            return "";
        }

        hasText(value, "Value can't be null");
        return endsWith(startWith(value));
    }

    public static String startWith(final String value) {
        return value + "%";
    }

    public static String endsWith(final String value) {
        hasText(value, "Value can't be null");
        return "%" + value;
    }
}