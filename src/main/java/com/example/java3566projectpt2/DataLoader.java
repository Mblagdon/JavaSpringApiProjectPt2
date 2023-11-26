package com.example.java3566projectpt2;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataLoader {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private ProgramsRepository programsRepository;
    @Autowired
    private EnrollmentRepository enrollmentRepository;
    @Autowired
    private GradesRepository gradesRepository;

    @PostConstruct
    public void loadData() {
        // Create and save students
        for (int i = 1; i <= 5; i++) {
            Student student = new Student();
            student.setFirstName("Marcus" + i);
            student.setLastName("Blagdon" + i);
            student.setEmail("marcusblagdon" + i + "@javaproject.com");
            student.setAddress("123 Main St");
            student.setCity("Burin" + i);
            student.setPostal("A0E 1E0" + i);
            student.setPhone("123-456-7890");
            studentRepository.save(student);
        }

        // Create and save courses
        for (int i = 1; i <= 2; i++) {
            Course course = new Course();
            course.setCourseName("Java" + i);
            course.setCourseNumber("CP3566" + i);
            course.setCapacity(30);
            course.setYear(2023);
            course.setSemester(Course.Semester.FALL);
            course.setPid((long) i);
            courseRepository.save(course);
        }

        // Create and save programs
        for (int i = 1; i <= 2; i++) {
            Programs program = new Programs();
            program.setProgramName("Software Development" + i);
            program.setCampus("PPD" + i);
            programsRepository.save(program);
        }

        // Create and save enrollments
        for (long i = 1; i <= 5; i++) {
            Enrollment enrollment = new Enrollment();
            enrollment.setCourseId(i % 2 + 1);
            enrollment.setStudentId(i);
            enrollmentRepository.save(enrollment);
        }

        // Create and save grades
        for (long i = 1; i <= 5; i++) {
            Grades grade = new Grades();
            grade.setStudentId(i);
            grade.setCourseId(i % 2 + 1);
            grade.setGrade("A");
            gradesRepository.save(grade);
        }
    }
}
