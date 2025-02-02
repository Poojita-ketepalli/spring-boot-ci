package com.app.studentapp.bean;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@ToString(exclude = {"students", "teachers"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Section {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sectionName;

    @OneToMany(mappedBy = "section", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Student> students;

    @OneToMany(mappedBy = "section", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Teacher> teachers;
}
