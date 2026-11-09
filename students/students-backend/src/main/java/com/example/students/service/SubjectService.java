package com.example.students.service;

import com.example.students.dto.SubjectRequest;
import com.example.students.exception.NotFoundException;
import com.example.students.model.Subject;
import com.example.students.model.Teacher;
import com.example.students.repository.SubjectRepository;
import com.example.students.repository.TeacherRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SubjectService {

    private final SubjectRepository subjects;
    private final TeacherRepository teachers;

    public SubjectService(SubjectRepository subjects, TeacherRepository teachers) {
        this.subjects = subjects;
        this.teachers = teachers;
    }

    public List<Subject> findAll() {
        return subjects.findAll();
    }

    public Subject get(Long id) {
        return subjects.findById(id)
                .orElseThrow(() -> new NotFoundException("Предмет не найден: id=" + id));
    }

    @Transactional
    public Subject create(SubjectRequest r) {
        Subject s = new Subject();
        apply(s, r);
        return subjects.save(s);
    }

    @Transactional
    public Subject update(Long id, SubjectRequest r) {
        Subject s = get(id);
        apply(s, r);
        return subjects.save(s);
    }

    @Transactional
    public void delete(Long id) {
        subjects.delete(get(id));
    }

    private void apply(Subject s, SubjectRequest r) {
        Teacher t = teachers.findById(r.teacherId())
                .orElseThrow(() -> new NotFoundException("Преподаватель не найден: id=" + r.teacherId()));
        s.setName(r.name());
        s.setHours(r.hours());
        s.setTeacher(t);
    }
}
