package com.example.students.dto;

public record StudentAverageDto(Long studentId, String firstName, String lastName,
                                String groupName, Double average, Long gradesCount) {
    public StudentAverageDto {
        average = average == null ? null : Math.round(average * 100.0) / 100.0;
    }
}
