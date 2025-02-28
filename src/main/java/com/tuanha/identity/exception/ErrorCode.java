package com.tuanha.identity.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.Getter;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorize error.", HttpStatus.INTERNAL_SERVER_ERROR),
    USERNAME_MIN_LENGTH(1003, "Username and password must be at least 6 characters.", HttpStatus.BAD_REQUEST),
    PASSWORD_MIN_LENGTH(1004, "Password and password must be at least 6 characters.", HttpStatus.BAD_REQUEST),
    USER_EXISTS(1001, "User already exists.", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTS(1005, "User not exists.", HttpStatus.NOT_FOUND),
    USER_NOT_FOUND(1002, "User not found.", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1006, "Unauthenticated.", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "You don't have permission.", HttpStatus.FORBIDDEN),
    PERMISSION_EXISTS(1008, "Permission already exists.", HttpStatus.BAD_REQUEST),
    ROLE_EXISTS(1009, "Role already exists.", HttpStatus.BAD_REQUEST),
    ROLE_NOT_FOUND(1010, "Role not found.", HttpStatus.NOT_FOUND),
    DOB_INVALID(1011, "Your age must be at least {min}.", HttpStatus.BAD_REQUEST),
    INVALID_TOKEN(1012, "Invalid token.", HttpStatus.UNAUTHORIZED)
    ;

    ErrorCode(int code, String message, HttpStatusCode httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    private int code;
    private String message;
    private HttpStatusCode httpStatus;
}
