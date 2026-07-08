package com.bc.cleaning.util;

import java.util.regex.Pattern;

public final class ValidationUtil {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    private ValidationUtil() {
    }

    public static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    public static String clean(String value) {
        return value == null ? null : value.trim();
    }

    public static String optional(String value) {
        String cleaned = clean(value);
        return isBlank(cleaned) ? null : cleaned;
    }

    public static boolean isValidEmail(String value) {
        return isBlank(value) || EMAIL_PATTERN.matcher(value.trim()).matches();
    }

    public static int parsePositiveInt(String raw, String fieldName) throws IllegalArgumentException {
        int value = parseInt(raw, fieldName);
        if (value <= 0) {
            throw new IllegalArgumentException(fieldName + " must be greater than zero.");
        }
        return value;
    }

    public static int parseNonNegativeInt(String raw, String fieldName) throws IllegalArgumentException {
        int value = parseInt(raw, fieldName);
        if (value < 0) {
            throw new IllegalArgumentException(fieldName + " cannot be negative.");
        }
        return value;
    }

    public static Integer parseOptionalInt(String raw) {
        if (isBlank(raw)) {
            return null;
        }
        return Integer.parseInt(raw.trim());
    }

    private static int parseInt(String raw, String fieldName) throws IllegalArgumentException {
        try {
            return Integer.parseInt(raw.trim());
        } catch (Exception ex) {
            throw new IllegalArgumentException(fieldName + " must be a valid number.");
        }
    }
}
