package com.batdemir.github.repo.list.helper;

import java.util.Locale;

public class GlobalVariable {
    private GlobalVariable() {

    }

    private static Locale locale = null;

    public static Locale getLocale() {
        return locale;
    }

    public static void setLocale(Locale locale) {
        GlobalVariable.locale = locale;
    }
}
