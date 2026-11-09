package com.example.students.controller;

import com.example.students.dto.StudentRequest;
import com.example.students.model.Grade;
import com.example.students.model.Student;
import com.example.students.service.GradeService;
import com.example.students.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService service;
    private final GradeService gradeService;

    public StudentController(StudentService service, GradeService gradeService) {
        this.service = service;
        this.gradeService = gradeService;
    }

    // GET /api/students  или  GET /api/students?search=иван  (поиск)
    @GetMapping
    public List<Student> list(@RequestParam(required = false) String search) {
        return service.findAll(search);
    }

    @GetMapping("/{id}")
    public Student get(@PathVariable Long id) {
        return service.get(id);
    }

    // Все оценки конкретного студента
    @GetMapping("/{id}/grades")
    public List<Grade> grades(@PathVariable Long id) {
        service.get(id); // 404, если студента нет
        return gradeService.findAll(id, null);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Student create(@Valid @RequestBody StudentRequest request) {
        return service.create(request);
    }

    @PutMapping("/{id}")
    public Student update(@PathVariable Long id, @Valid @RequestBody StudentRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
