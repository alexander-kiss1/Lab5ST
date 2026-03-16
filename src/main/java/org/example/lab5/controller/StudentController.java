package org.example.lab5.controller;

import jakarta.validation.Valid;
import org.example.lab5.model.Student;
import org.example.lab5.repository.StudentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentRepository repository;

    public StudentController(StudentRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Student> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Student> getStudent(@PathVariable long id) {
        return repository.findById(id);
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> create(@Valid @RequestBody Student student) {
        repository.save(student);

        Map<String, String> response = new HashMap<>();
        response.put("message", student.getName() + " added successfully");

        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<Map<String, String>> update(@Valid @RequestBody Student student) {
        repository.save(student);

        Map<String, String> response = new HashMap<>();
        response.put("message", student.getName() + " updated successfully");

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }
}