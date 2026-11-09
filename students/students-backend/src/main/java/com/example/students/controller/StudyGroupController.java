package com.example.students.controller;

import com.example.students.exception.NotFoundException;
import com.example.students.model.StudyGroup;
import com.example.students.repository.StudyGroupRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
public class StudyGroupController {

    private final StudyGroupRepository repo;

    public StudyGroupController(StudyGroupRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<StudyGroup> list() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public StudyGroup get(@PathVariable Long id) {
        return repo.findById(id).orElseThrow(() -> new NotFoundException("Группа не найдена: id=" + id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StudyGroup create(@Valid @RequestBody StudyGroup group) {
        group.setId(null);
        return repo.save(group);
    }

    @PutMapping("/{id}")
    public StudyGroup update(@PathVariable Long id, @Valid @RequestBody StudyGroup body) {
        StudyGroup g = get(id);
        g.setName(body.getName());
        g.setAdmissionYear(body.getAdmissionYear());
        return repo.save(g);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        repo.delete(get(id));
    }
}
