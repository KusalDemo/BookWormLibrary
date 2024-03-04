package org.example.Util;

import java.util.regex.Pattern;

public class Regex {
    public static boolean isPasswordValid(String Password) {
        boolean matches = Pattern.matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$", Password);
        return matches;
    }
    public static boolean isEmailValid(String email) {
        boolean matches = Pattern.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", email);
        return matches;
    }
}
