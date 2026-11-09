package com.example.students.controller;

import com.example.students.dto.SubjectRequest;
import com.example.students.model.Subject;
import com.example.students.service.SubjectService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subjects")
public class SubjectController {

    private final SubjectService service;

    public SubjectController(SubjectService service) {
        this.service = service;
    }

    @GetMapping
    public List<Subject> list() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Subject get(@PathVariable Long id) {
        return service.get(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Subject create(@Valid @RequestBody SubjectRequest request) {
        return service.create(request);
    }

    @PutMapping("/{id}")
    public Subject update(@PathVariable Long id, @Valid @RequestBody SubjectRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
