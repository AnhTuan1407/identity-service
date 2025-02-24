package com.tuanha.identity.dto.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateRequest {
    @Size(min = 6, message = "USERNAME_MIN_LENGTH")
    private String username;
    @Size(min = 6, message = "PASSWORD_MIN_LENGTH")
    private String password;
    private String firstName;
    private String lastName;
}
