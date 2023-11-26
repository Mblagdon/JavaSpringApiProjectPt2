package com.example.java3566projectpt2;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface GradesRepository extends JpaRepository<Grades, Long> {
    List<Grades> findByStudentId(Long studentId);
    List<Grades> findByCourseId(Long courseId);
}

