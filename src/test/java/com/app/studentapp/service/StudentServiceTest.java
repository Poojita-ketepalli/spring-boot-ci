package com.app.studentapp.service;

import com.app.studentapp.bean.Student;
import com.app.studentapp.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    private Student student1;
    private Student student2;

    @BeforeEach
    void setUp() {
        student1 = new Student();
        student1.setId(1L);
        student1.setName("John Doe");
        student1.setAge(22);

        student2 = new Student();
        student2.setId(2L);
        student2.setName("Jane Smith");
        student2.setAge(21);
    }

    @Test
    void testGetAllStudents() {
        List<Student> students = Arrays.asList(student1, student2);
        when(studentRepository.findAll()).thenReturn(students);

        List<Student> result = studentService.getAllStudents();

        assertEquals(2, result.size());
        verify(studentRepository, times(1)).findAll();
    }

    @Test
    void testGetStudentById_Found() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student1));

        Optional<Student> result = studentService.getStudentById(1L);

        assertTrue(result.isPresent());
        assertEquals("John Doe", result.get().getName());
        verify(studentRepository, times(1)).findById(1L);
    }

    @Test
    void testGetStudentById_NotFound() {
        when(studentRepository.findById(3L)).thenReturn(Optional.empty());

        Optional<Student> result = studentService.getStudentById(3L);

        assertFalse(result.isPresent());
        verify(studentRepository, times(1)).findById(3L);
    }

    @Test
    void testAddStudent() {
        when(studentRepository.save(any(Student.class))).thenReturn(student1);

        Student result = studentService.addStudent(student1);

        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        verify(studentRepository, times(1)).save(student1);
    }

    @Test
    void testUpdateStudent_found() {
        // Arrange - Mocking existing student
        Long studentId = 1L;
        Student existingStudent = new Student();
        existingStudent.setId(studentId);
        existingStudent.setName("John Doe");
        existingStudent.setAge(22);

        Student updatedStudent = new Student();
        updatedStudent.setId(studentId);
        updatedStudent.setName("Jane Doe"); // Updated name
        updatedStudent.setAge(23); // Updated age

        // Mock repository behavior
        when(studentRepository.existsById(studentId)).thenReturn(true);
        when(studentRepository.save(any(Student.class))).thenReturn(updatedStudent);

        // Act - Calling update method
        Student result = studentService.updateStudent(studentId, updatedStudent);

        // Assert - Verifying the update process
        assertNotNull(result);
        assertEquals(updatedStudent.getId(), result.getId());
        assertEquals("Jane Doe", result.getName());
        assertEquals(23, result.getAge());

        // Verify interactions
        verify(studentRepository, times(1)).existsById(studentId);
        verify(studentRepository, times(1)).save(updatedStudent);
    }


    @Test
    void testUpdateStudent_NotFound() {
        when(studentRepository.existsById(3L)).thenReturn(false);

        Student updatedStudent = new Student();
        updatedStudent.setName("Updated Name");
        updatedStudent.setAge(25);

        Student result = studentService.updateStudent(3L, updatedStudent);

        assertNull(result);
        verify(studentRepository, times(1)).existsById(3L);
        verify(studentRepository, never()).save(updatedStudent);
    }

    @Test
    void testDeleteStudent() {
        doNothing().when(studentRepository).deleteById(1L);

        studentService.deleteStudent(1L);

        verify(studentRepository, times(1)).deleteById(1L);
    }
}
