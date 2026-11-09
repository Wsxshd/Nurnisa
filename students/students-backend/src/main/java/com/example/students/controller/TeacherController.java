package com.example.students.controller;

import com.example.students.exception.NotFoundException;
import com.example.students.model.Teacher;
import com.example.students.repository.TeacherRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teachers")
public class TeacherController {

    private final TeacherRepository repo;

    public TeacherController(TeacherRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Teacher> list() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public Teacher get(@PathVariable Long id) {
        return repo.findById(id).orElseThrow(() -> new NotFoundException("Преподаватель не найден: id=" + id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Teacher create(@Valid @RequestBody Teacher teacher) {
        teacher.setId(null);
        return repo.save(teacher);
    }

    @PutMapping("/{id}")
    public Teacher update(@PathVariable Long id, @Valid @RequestBody Teacher body) {
        Teacher t = get(id);
        t.setFullName(body.getFullName());
        t.setEmail(body.getEmail());
        return repo.save(t);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        repo.delete(get(id));
    }
}
