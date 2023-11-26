package com.example.java3566projectpt2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/grades")
public class GradesController {
    @Autowired
    private GradesRepository gradesRepository;

    // Create
    @PostMapping("/add")
    public @ResponseBody String addGrade(@RequestBody Grades grade) {
        gradesRepository.save(grade);
        return "Grade saved";
    }

    // Read all
    @GetMapping("/all")
    public @ResponseBody List<Grades> getAllGrades() {
        return gradesRepository.findAll();
    }

    // Read one
    @GetMapping("/{id}")
    public @ResponseBody ResponseEntity<Grades> getGradeById(@PathVariable Long id) {
        return gradesRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update
    @PutMapping("/{id}")
    public @ResponseBody ResponseEntity<Grades> updateGrade(@PathVariable Long id, @RequestBody Grades gradeDetails) {
        return gradesRepository.findById(id)
                .map(grade -> {
                    grade.setStudentId(gradeDetails.getStudentId());
                    grade.setCourseId(gradeDetails.getCourseId());
                    grade.setGrade(gradeDetails.getGrade());
                    gradesRepository.save(grade);
                    return ResponseEntity.ok(grade);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Delete
    @DeleteMapping("/{id}")
    public @ResponseBody ResponseEntity<String> deleteGrade(@PathVariable Long id) {
        if (gradesRepository.existsById(id)) {
            gradesRepository.deleteById(id);
            return ResponseEntity.ok("Grade deleted successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/student/{studentId}")
    public @ResponseBody ResponseEntity<List<Grades>> getGradesByStudent(@PathVariable Long studentId) {
        List<Grades> grades = gradesRepository.findByStudentId(studentId);
        if (!grades.isEmpty()) {
            return ResponseEntity.ok(grades);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // List all grades for a specific course
    @GetMapping("/course/{courseId}")
    public @ResponseBody ResponseEntity<List<Grades>> getGradesByCourse(@PathVariable Long courseId) {
        List<Grades> grades = gradesRepository.findByCourseId(courseId);
        if (!grades.isEmpty()) {
            return ResponseEntity.ok(grades);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}