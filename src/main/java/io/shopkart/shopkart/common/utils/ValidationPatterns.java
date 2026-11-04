package io.shopkart.shopkart.common.utils;

public final class ValidationPatterns {

    public static final String PASSWORD = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,72}$";
    public static final String PASSWORD_MESSAGE =
            "Password must be 8-72 characters and contain an uppercase letter, a lowercase letter and a digit";

    public static final String PHONE = "^\\+?[0-9]{7,15}$";
    public static final String PHONE_MESSAGE = "Phone must be 7-15 digits, optionally starting with +";

    public static final String POSTAL_CODE = "^[A-Za-z0-9 -]{3,12}$";
}
