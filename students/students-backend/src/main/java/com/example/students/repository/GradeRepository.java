package com.example.students.repository;

import com.example.students.dto.GroupStatsDto;
import com.example.students.dto.StudentAverageDto;
import com.example.students.dto.SubjectStatsDto;
import com.example.students.model.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GradeRepository extends JpaRepository<Grade, Long> {

    List<Grade> findByStudentId(Long studentId);

    List<Grade> findBySubjectId(Long subjectId);

    List<Grade> findByStudentIdAndSubjectId(Long studentId, Long subjectId);

    // ----- Отчеты: JOIN + агрегатные функции -----

    @Query("""
           select new com.example.students.dto.StudentAverageDto(
               s.id, s.firstName, s.lastName, gr.name, avg(g.score), count(g))
           from Grade g
             join g.student s
             join s.group gr
           group by s.id, s.firstName, s.lastName, gr.name
           order by avg(g.score) desc
           """)
    List<StudentAverageDto> studentsAverage();

    @Query("""
           select new com.example.students.dto.SubjectStatsDto(
               sub.id, sub.name, t.fullName, avg(g.score), min(g.score), max(g.score), count(g))
           from Grade g
             join g.subject sub
             join sub.teacher t
           group by sub.id, sub.name, t.fullName
           order by sub.name
           """)
    List<SubjectStatsDto> subjectsStats();

    @Query("""
           select new com.example.students.dto.GroupStatsDto(
               gr.id, gr.name, avg(g.score), count(distinct s.id), count(g))
           from Grade g
             join g.student s
             join s.group gr
           group by gr.id, gr.name
           order by avg(g.score) desc
           """)
    List<GroupStatsDto> groupsStats();
}
