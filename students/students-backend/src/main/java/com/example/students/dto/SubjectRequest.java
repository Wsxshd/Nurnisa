package com.example.students.dto;

import jakarta.validation.constraints.*;

public record SubjectRequest(
        @NotBlank(message = "Название предмета обязательно") @Size(max = 150) String name,
        @NotNull(message = "Количество часов обязательно") @Min(value = 1, message = "Часов должно быть >= 1") Integer hours,
        @NotNull(message = "teacherId обязателен") Long teacherId) {
}
