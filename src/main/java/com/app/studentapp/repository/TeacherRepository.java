package com.app.studentapp.repository;

import com.app.studentapp.bean.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
}
