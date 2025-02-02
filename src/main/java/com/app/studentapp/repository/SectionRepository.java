package com.app.studentapp.repository;

import com.app.studentapp.bean.Section;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SectionRepository extends JpaRepository<Section, Long> {
}
