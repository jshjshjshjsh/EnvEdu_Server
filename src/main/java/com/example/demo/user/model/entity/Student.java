package com.example.demo.user.model.entity;

import com.example.demo.user.model.enumerate.Gender;
import com.example.demo.user.model.enumerate.State;
import com.example.demo.user.model.enumerate.Role;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.sql.Date;
import java.util.List;

@Entity
public class Student extends User {
    public Student() {}

    @Builder(builderMethodName = "studentBuilder")
    public Student(String username, String password, String email, Date birthday, Role role, Gender gender, State state)
    {
        super(username, password, email, birthday, role, gender, state);
    }

    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
    private List<Student_Educator> student_educators;

    public List<Student_Educator> getStudent_educators() {
        return student_educators;
    }
}
