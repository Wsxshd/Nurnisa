package com.example.students.service;

import com.example.students.dto.GradeRequest;
import com.example.students.exception.NotFoundException;
import com.example.students.model.Grade;
import com.example.students.repository.GradeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class GradeService {

    private final GradeRepository grades;
    private final StudentService studentService;
    private final SubjectService subjectService;

    public GradeService(GradeRepository grades, StudentService studentService, SubjectService subjectService) {
        this.grades = grades;
        this.studentService = studentService;
        this.subjectService = subjectService;
    }

    // Фильтры studentId / subjectId необязательные
    public List<Grade> findAll(Long studentId, Long subjectId) {
        if (studentId != null && subjectId != null) return grades.findByStudentIdAndSubjectId(studentId, subjectId);
        if (studentId != null) return grades.findByStudentId(studentId);
        if (subjectId != null) return grades.findBySubjectId(subjectId);
        return grades.findAll();
    }

    public Grade get(Long id) {
        return grades.findById(id)
                .orElseThrow(() -> new NotFoundException("Оценка не найдена: id=" + id));
    }

    @Transactional
    public Grade create(GradeRequest r) {
        Grade g = new Grade();
        apply(g, r);
        return grades.save(g);
    }

    @Transactional
    public Grade update(Long id, GradeRequest r) {
        Grade g = get(id);
        apply(g, r);
        return grades.save(g);
    }

    @Transactional
    public void delete(Long id) {
        grades.delete(get(id));
    }

    private void apply(Grade g, GradeRequest r) {
        g.setStudent(studentService.get(r.studentId()));
        g.setSubject(subjectService.get(r.subjectId()));
        g.setScore(r.score());
        g.setGradeDate(r.gradeDate() != null ? r.gradeDate() : LocalDate.now());
    }
}
