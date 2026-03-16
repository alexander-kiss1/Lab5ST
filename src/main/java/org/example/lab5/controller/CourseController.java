package org.example.lab5.controller;

import jakarta.validation.Valid;
import org.example.lab5.dto.CourseRequest;
import org.example.lab5.dto.CourseResponse;
import org.example.lab5.exception.NullCourseException;
import org.example.lab5.exception.NullStudentException;
import org.example.lab5.model.Course;
import org.example.lab5.model.Student;
import org.example.lab5.repository.CourseRepository;
import org.example.lab5.repository.StudentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseRepository repository;
    private final StudentRepository studentRepository;

    public CourseController(CourseRepository repository, StudentRepository studentRepository) {
        this.repository = repository;
        this.studentRepository = studentRepository;
    }

    @GetMapping
    public List<CourseResponse> getCourses() {
        List<Course> courses = repository.findAll();

        return courses.stream()
                .map(course -> {
                    CourseResponse response = new CourseResponse();
                    response.setId(course.getId());
                    response.setName(course.getName());
                    response.setInstructor(course.getInstructor());
                    response.setRoom(course.getRoom());
                    response.setRoster(
                            course.getRoster().stream()
                                    .map(Student::getName)
                                    .collect(Collectors.toSet())
                    );
                    return response;
                })
                .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<String> create(@Valid @RequestBody CourseRequest request) {
        Course course = new Course();
        course.setName(request.getName());
        course.setInstructor(request.getInstructor());
        course.setMaxSize(request.getMaxSize());
        course.setRoom(request.getRoom());

        if (request.getRoster() != null) {
            List<Student> studentsFromDb = studentRepository.findAllById(request.getRoster());
            course.setRoster(new HashSet<>(studentsFromDb));
        }

        repository.save(course);
        return ResponseEntity.ok(course.getName() + " added successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@Valid @RequestBody CourseRequest request, @PathVariable Long id) {
        Course course = repository.findById(id).get();

        course.setName(request.getName());
        course.setInstructor(request.getInstructor());
        course.setMaxSize(request.getMaxSize());
        course.setRoom(request.getRoom());

        if (request.getRoster() != null) {
            List<Student> studentsFromDb = studentRepository.findAllById(request.getRoster());
            course.setRoster(new HashSet<>(studentsFromDb));
        }

        repository.save(course);
        return ResponseEntity.ok(course.getName() + " updated successfully");
    }

    @PutMapping("/addStudent/{courseId}")
    public ResponseEntity<String> addStudent(@PathVariable Long courseId, @RequestBody long studentId) {
        try {
            Course course = repository.findById(courseId).orElseThrow(NullCourseException::new);
            Student student = studentRepository.findById(studentId).orElseThrow(NullStudentException::new);

            if (course.getRoster().size() < course.getMaxSize()) {
                course.getRoster().add(student);
                repository.save(course);
                return ResponseEntity.ok(student.getName() + " added successfully");
            }

            return ResponseEntity.badRequest().body("Course is full");
        } catch (NullCourseException | NullStudentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/removeStudent/{courseId}")
    public ResponseEntity<String> removeStudent(@PathVariable Long courseId, @RequestBody long studentId) {
        try {
            Course course = repository.findById(courseId).orElseThrow(NullCourseException::new);
            Student student = studentRepository.findById(studentId).orElseThrow(NullStudentException::new);

            if (course.getRoster().contains(student)) {
                course.getRoster().remove(student);
                repository.save(course);
                return ResponseEntity.ok(student.getName() + " removed successfully");
            }

            return ResponseEntity.badRequest().body("Student not in that course");
        } catch (NullCourseException | NullStudentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/getEnrollment/{courseId}")
    public ResponseEntity<Long> getEnrollment(@PathVariable Long courseId) {
        try {
            Course course = repository.findById(courseId).orElseThrow(NullCourseException::new);
            return ResponseEntity.ok((long) course.getRoster().size());
        } catch (NullCourseException e) {
            return ResponseEntity.badRequest().body(-1L);
        }
    }
}