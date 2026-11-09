package com.example.students.controller;

import com.example.students.dto.GroupStatsDto;
import com.example.students.dto.StudentAverageDto;
import com.example.students.dto.SubjectStatsDto;
import com.example.students.repository.GradeRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final GradeRepository grades;

    public ReportController(GradeRepository grades) {
        this.grades = grades;
    }

    // Средний балл каждого студента (рейтинг)
    @GetMapping("/students-average")
    public List<StudentAverageDto> studentsAverage() {
        return grades.studentsAverage();
    }

    // Статистика по предметам: средний, мин, макс, кол-во оценок, преподаватель
    @GetMapping("/subjects-stats")
    public List<SubjectStatsDto> subjectsStats() {
        return grades.subjectsStats();
    }

    // Статистика по группам
    @GetMapping("/groups-stats")
    public List<GroupStatsDto> groupsStats() {
        return grades.groupsStats();
    }
}
