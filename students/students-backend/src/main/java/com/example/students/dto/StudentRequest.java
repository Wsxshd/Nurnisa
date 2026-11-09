package com.example.students.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record StudentRequest(
        @NotBlank(message = "Имя обязательно") @Size(max = 100) String firstName,
        @NotBlank(message = "Фамилия обязательна") @Size(max = 100) String lastName,
        @NotBlank(message = "Email обязателен") @Email(message = "Некорректный email") String email,
        @Past(message = "Дата рождения должна быть в прошлом") LocalDate birthDate,
        @NotNull(message = "groupId обязателен") Long groupId) {
}
