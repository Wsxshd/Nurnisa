package com.example.students.controller;

import com.example.students.dto.GradeRequest;
import com.example.students.model.Grade;
import com.example.students.service.GradeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grades")
public class GradeController {

    private final GradeService service;

    public GradeController(GradeService service) {
        this.service = service;
    }

    // GET /api/grades?studentId=1&subjectId=2  (фильтры необязательны)
    @GetMapping
    public List<Grade> list(@RequestParam(required = false) Long studentId,
                            @RequestParam(required = false) Long subjectId) {
        return service.findAll(studentId, subjectId);
    }

    @GetMapping("/{id}")
    public Grade get(@PathVariable Long id) {
        return service.get(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Grade create(@Valid @RequestBody GradeRequest request) {
        return service.create(request);
    }

    @PutMapping("/{id}")
    public Grade update(@PathVariable Long id, @Valid @RequestBody GradeRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
