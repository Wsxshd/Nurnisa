package com.example.students.dto;

public record SubjectStatsDto(Long subjectId, String subjectName, String teacherName,
                              Double average, Integer minScore, Integer maxScore, Long gradesCount) {
    public SubjectStatsDto {
        average = average == null ? null : Math.round(average * 100.0) / 100.0;
    }
}
