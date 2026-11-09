package com.example.students.dto;

public record GroupStatsDto(Long groupId, String groupName, Double average,
                            Long studentsCount, Long gradesCount) {
    public GroupStatsDto {
        average = average == null ? null : Math.round(average * 100.0) / 100.0;
    }
}
