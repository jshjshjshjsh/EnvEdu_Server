package com.example.demo.user.model.entity;

import com.example.demo.user.model.enumerate.Gender;
import com.example.demo.user.model.enumerate.State;
import com.example.demo.user.model.enumerate.IsAuthorized;
import com.example.demo.user.model.enumerate.Role;
import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
public class Educator extends User {
    public Educator() {}

    @Builder(builderMethodName = "educatorBuilder")
    public Educator(String username, String password, String email, Date birthday, Role role, Gender gender, State state, IsAuthorized isAuthorized)
    {
        super(username, password, email, birthday, role, gender, state);
        this.isAuthorized = isAuthorized;
    }

    @OneToMany(mappedBy = "educator", fetch = FetchType.LAZY)
    private List<Student_Educator> educator_students;

    @Column(length = 3, nullable = false)
    @Enumerated(EnumType.STRING)
    private IsAuthorized isAuthorized;

    public List<Student_Educator> getEducator_students() {
        return educator_students;
    }

    public IsAuthorized getIsAuthorized() {
        return isAuthorized;
    }

    public void updateAuthorization(IsAuthorized isAuthorized) {
        this.isAuthorized = isAuthorized;
    }
}
