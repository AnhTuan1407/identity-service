package com.tuanha.identity.dto.request;

import java.time.LocalDate;
import java.util.List;

import com.tuanha.identity.validation.DobConstraint;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreateRequest {
    @Size(min = 6, message = "USERNAME_MIN_LENGTH")
    String username;
    @Size(min = 6, message = "PASSWORD_MIN_LENGTH")
    String password;
    String firstName;
    String lastName;

    @DobConstraint(min = 16, message = "DOB_INVALID")
    LocalDate dob;
}
