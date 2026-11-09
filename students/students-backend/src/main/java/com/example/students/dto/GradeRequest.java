package com.example.students.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record GradeRequest(
        @NotNull(message = "studentId обязателен") Long studentId,
        @NotNull(message = "subjectId обязателен") Long subjectId,
        @NotNull(message = "Оценка обязательна")
        @Min(value = 1, message = "Оценка от 1 до 5") @Max(value = 5, message = "Оценка от 1 до 5") Integer score,
        @PastOrPresent(message = "Дата не может быть в будущем") LocalDate gradeDate) {
}
