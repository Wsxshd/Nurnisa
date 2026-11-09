package com.example.students.service;

import com.example.students.dto.StudentRequest;
import com.example.students.exception.NotFoundException;
import com.example.students.model.Student;
import com.example.students.model.StudyGroup;
import com.example.students.repository.StudentRepository;
import com.example.students.repository.StudyGroupRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository students;
    private final StudyGroupRepository groups;

    public StudentService(StudentRepository students, StudyGroupRepository groups) {
        this.students = students;
        this.groups = groups;
    }

    public List<Student> findAll(String search) {
        if (search == null || search.isBlank()) {
            return students.findAll();
        }
        return students.findByLastNameContainingIgnoreCaseOrFirstNameContainingIgnoreCase(search, search);
    }

    public Student get(Long id) {
        return students.findById(id)
                .orElseThrow(() -> new NotFoundException("Студент не найден: id=" + id));
    }

    @Transactional
    public Student create(StudentRequest r) {
        Student s = new Student();
        apply(s, r);
        return students.save(s);
    }

    @Transactional
    public Student update(Long id, StudentRequest r) {
        Student s = get(id);
        apply(s, r);
        return students.save(s);
    }

    @Transactional
    public void delete(Long id) {
        students.delete(get(id));
    }

    private void apply(Student s, StudentRequest r) {
        StudyGroup group = groups.findById(r.groupId())
                .orElseThrow(() -> new NotFoundException("Группа не найдена: id=" + r.groupId()));
        s.setFirstName(r.firstName());
        s.setLastName(r.lastName());
        s.setEmail(r.email());
        s.setBirthDate(r.birthDate());
        s.setGroup(group);
    }
}
