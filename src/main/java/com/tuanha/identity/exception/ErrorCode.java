package com.tuanha.identity.exception;

public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorize error."),
    USERNAME_MIN_LENGTH(1003, "Username and password must be at least 6 characters."),
    PASSWORD_MIN_LENGTH(1004, "Password and password must be at least 6 characters."),
    USER_EXISTS(1001, "User already exists."),
    USER_NOT_EXISTS(1005, "User already exists."),
    UNAUTHENTICATED(1006, "Unauthenticated."),
    USER_NOT_FOUND(1002, "User not found.");

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
