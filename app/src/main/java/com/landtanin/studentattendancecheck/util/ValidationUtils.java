package com.landtanin.studentattendancecheck.util;

import java.util.regex.Pattern;

/**
 * Utility class providing validation methods for authentication and user inputs.
 * Separated from Android SDK classes to enable clean unit testing on the JVM.
 */
public final class ValidationUtils {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"
    );

    public static final int DEFAULT_MIN_PASSWORD_LENGTH = 4;

    private ValidationUtils() {
        // Prevent instantiation
    }

    /**
     * Validates whether a given email address conforms to standard email format.
     *
     * @param email Email string to validate
     * @return true if non-null and valid format, false otherwise
     */
    public static boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }
        String trimmed = email.trim();
        return !trimmed.isEmpty() && EMAIL_PATTERN.matcher(trimmed).matches();
    }

    /**
     * Validates whether a password meets the default minimum length requirement.
     *
     * @param password Password string to validate
     * @return true if non-null and length >= DEFAULT_MIN_PASSWORD_LENGTH, false otherwise
     */
    public static boolean isValidPassword(String password) {
        return isValidPassword(password, DEFAULT_MIN_PASSWORD_LENGTH);
    }

    /**
     * Validates whether a password meets a specific minimum length requirement.
     *
     * @param password  Password string to validate
     * @param minLength Minimum acceptable length
     * @return true if non-null and length >= minLength, false otherwise
     */
    public static boolean isValidPassword(String password, int minLength) {
        if (password == null) {
            return false;
        }
        return password.length() >= minLength;
    }

    /**
     * Validates whether a student ID is a valid positive identifier.
     *
     * @param studentId Student ID integer
     * @return true if studentId > 0, false otherwise
     */
    public static boolean isValidStudentId(int studentId) {
        return studentId > 0;
    }

    /**
     * Validates that all required login input fields are valid.
     *
     * @param email    Email address input
     * @param password Password input
     * @return true if both email and password are valid, false otherwise
     */
    public static boolean isValidLoginInput(String email, String password) {
        return isValidEmail(email) && isValidPassword(password);
    }
}
