package com.example.java3566projectpt2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/enrollment")
public class EnrollmentController {
    @Autowired
    private EnrollmentRepository enrollmentRepository;

    // Create
    @PostMapping("/add")
    public @ResponseBody String addEnrollment(@RequestBody Enrollment enrollment) {
        enrollmentRepository.save(enrollment);
        return "Enrollment saved";
    }

    // Read all
    @GetMapping("/all")
    public @ResponseBody List<Enrollment> getAllEnrollments() {
        return enrollmentRepository.findAll();
    }

    // Read one
    @GetMapping("/{id}")
    public @ResponseBody ResponseEntity<Enrollment> getEnrollmentById(@PathVariable Long id) {
        return enrollmentRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update
    @PutMapping("/{id}")
    public @ResponseBody ResponseEntity<Enrollment> updateEnrollment(@PathVariable Long id, @RequestBody Enrollment enrollmentDetails) {
        return enrollmentRepository.findById(id)
                .map(enrollment -> {
                    enrollment.setCourseId(enrollmentDetails.getCourseId());
                    enrollment.setStudentId(enrollmentDetails.getStudentId());
                    enrollmentRepository.save(enrollment);
                    return ResponseEntity.ok(enrollment);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Delete
    @DeleteMapping("/{id}")
    public @ResponseBody ResponseEntity<String> deleteEnrollment(@PathVariable Long id) {
        if (enrollmentRepository.existsById(id)) {
            enrollmentRepository.deleteById(id);
            return ResponseEntity.ok("Enrollment deleted successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/course/{courseId}")
    public @ResponseBody ResponseEntity<List<Enrollment>> getEnrollmentsByCourse(@PathVariable Long courseId) {
        List<Enrollment> enrollments = enrollmentRepository.findByCourseId(courseId);
        if (!enrollments.isEmpty()) {
            return ResponseEntity.ok(enrollments);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // List all enrollments for a specific student
    @GetMapping("/student/{studentId}")
    public @ResponseBody ResponseEntity<List<Enrollment>> getEnrollmentsByStudent(@PathVariable Long studentId) {
        List<Enrollment> enrollments = enrollmentRepository.findByStudentId(studentId);
        if (!enrollments.isEmpty()) {
            return ResponseEntity.ok(enrollments);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
