package org.example.app.utils;

public class AppValidator {

    public final static String ID_RGX = "^[1-9]$";
    public final static String PHONE_RGX = "[0-9]{3} [0-9]{3}-[0-9]{4}";
    public final static String EMAIL_RGX = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";

    public static boolean isIdValid(String id) {
        if (id != null)
            return id.isEmpty() || !id.matches(ID_RGX);
        return false;
    }

    public static boolean isPhoneValid(String phone) {
        if (phone != null)
            return phone.isEmpty() || !phone.matches(PHONE_RGX);
        return false;
    }

    public static boolean isEmailValid(String email) {
        if (email != null)
            return email.isEmpty() || !email.matches(EMAIL_RGX);
        return false;
    }
}
