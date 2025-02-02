package com.app.studentapp.controller;

import com.app.studentapp.bean.Student;
import com.app.studentapp.service.StudentService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
class StudentControllerTest {

    @Mock
    private StudentService studentService;

    @InjectMocks
    private StudentController studentController;

    @Test
    void testAddStudent() {
        // Create a test student
        Student student = new Student();
        student.setName("John Doe");
        student.setAge(22);

        when(studentService.addStudent(any(Student.class))).thenReturn(student);

        // Test POST method
        ResponseEntity<Student> response = studentController.addStudent(student);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
    }
}

