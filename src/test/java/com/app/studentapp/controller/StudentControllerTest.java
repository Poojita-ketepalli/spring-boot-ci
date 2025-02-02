package com.app.studentapp.controller;

import com.app.studentapp.bean.Student;
import com.app.studentapp.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentControllerTest {

    @Mock
    private StudentService studentService;

    @InjectMocks
    private StudentController studentController;

    private Student student1;
    private Student student2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Sample students
        student1 = new Student();
        student1.setId(1L);
        student1.setName("John Doe");
        student1.setAge(22);

        student2 = new Student();
        student2.setId(2L);
        student2.setName("Jane Doe");
        student2.setAge(20);
    }

    @Test
    void testGetAllStudents() {
        List<Student> students = Arrays.asList(student1, student2);
        when(studentService.getAllStudents()).thenReturn(students);

        List<Student> result = studentController.getAllStudents();

        assertEquals(2, result.size());
        assertEquals("John Doe", result.get(0).getName());
        verify(studentService, times(1)).getAllStudents();
    }

    @Test
    void testGetStudentById_Found() {
        when(studentService.getStudentById(1L)).thenReturn(Optional.of(student1));

        Optional<Student> result = studentController.getStudentById(1L);

        assertTrue(result.isPresent());
        assertEquals("John Doe", result.get().getName());
        verify(studentService, times(1)).getStudentById(1L);
    }

    @Test
    void testGetStudentById_NotFound() {
        when(studentService.getStudentById(3L)).thenReturn(Optional.empty());

        Optional<Student> result = studentController.getStudentById(3L);

        assertFalse(result.isPresent());
        verify(studentService, times(1)).getStudentById(3L);
    }

    @Test
    void testAddStudent() {
        when(studentService.addStudent(any(Student.class))).thenReturn(student1);

        ResponseEntity<Student> response = studentController.addStudent(student1);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("John Doe", response.getBody().getName());
        verify(studentService, times(1)).addStudent(any(Student.class));
    }

    @Test
    void testUpdateStudent() {
        when(studentService.updateStudent(eq(1L), any(Student.class))).thenReturn(student1);

        Student updatedStudent = studentController.updateStudent(1L, student1);

        assertNotNull(updatedStudent);
        assertEquals("John Doe", updatedStudent.getName());
        verify(studentService, times(1)).updateStudent(eq(1L), any(Student.class));
    }

    @Test
    void testDeleteStudent_Success() {
        doNothing().when(studentService).deleteStudent(1L);

        studentController.deleteStudent(1L);

        verify(studentService, times(1)).deleteStudent(1L);
    }
}
