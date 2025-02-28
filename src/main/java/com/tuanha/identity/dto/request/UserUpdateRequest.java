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
public class UserUpdateRequest {
    @Size(min = 6, message = "Password must be at least 6 characters")
    String password;
    String firstName;
    String lastName;

    @DobConstraint(min = 18, message = "DOB_INVALID")
    LocalDate dob;
    List<String> roles;
}