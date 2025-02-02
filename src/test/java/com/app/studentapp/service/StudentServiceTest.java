package com.app.studentapp.service;

import com.app.studentapp.bean.Student;
import com.app.studentapp.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    @Test
    void testAddStudent() {
        // Create a test student
        Student student = new Student();
        student.setName("John Doe");
        student.setAge(22);

        when(studentRepository.save(Mockito.any(Student.class))).thenReturn(student);

        // Test addStudent method
        Student addedStudent = studentService.addStudent(student);

        // Verify if save method was called
        verify(studentRepository).save(student);
    }

    @Test
    void testGetAllStudents() {
        // Create mock data
        List<Student> students = List.of(new Student(), new Student());

        when(studentRepository.findAll()).thenReturn(students);

        // Test getAllStudents method
        List<Student> fetchedStudents = studentService.getAllStudents();

        assertEquals(2, fetchedStudents.size());
    }
}

