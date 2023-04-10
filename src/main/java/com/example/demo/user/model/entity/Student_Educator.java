package com.example.demo.user.model.entity;

import javax.persistence.*;

@Entity
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
}
