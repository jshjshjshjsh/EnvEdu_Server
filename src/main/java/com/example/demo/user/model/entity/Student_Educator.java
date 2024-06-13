package com.example.demo.user.model.entity;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"educatorId", "studentId"}))
public class Student_Educator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "studentId")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "educatorId")
    private Educator educator;

    public Student_Educator() {}

    private Student_Educator(Student student, Educator educator) {
        this.student = student;
        this.educator = educator;
    }

    public static Student_Educator of(Student student, Educator educator) {
        return new Student_Educator(student, educator);
    }
}
