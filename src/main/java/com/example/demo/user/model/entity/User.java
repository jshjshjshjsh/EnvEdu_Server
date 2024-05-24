package com.example.demo.user.model.entity;

import com.example.demo.device.model.UserDevice;
import com.example.demo.datacontrol.datachunk.model.MeasuredUnit;
import com.example.demo.location.model.Location;
import com.example.demo.user.model.enumerate.Gender;
import com.example.demo.user.model.enumerate.State;
import com.example.demo.user.model.enumerate.Role;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.regex.Pattern;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User {
    protected User() {}

    protected User(String username, String password, String email, Date birthday, Role role, Gender gender, State state, String nickname) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.birthday = birthday;
        this.role = role;
        this.gender = gender;
        this.state = state;
        this.nickname = nickname;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 20, unique = true)
    private String username;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false, length = 40, unique = true)
    private String email;

    @Column(nullable = false)
    private Date birthday;

    @Column(nullable = false, length = 13)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false, length = 6)
    @Enumerated(EnumType.STRING)
    @Nullable
    private Gender gender;

    @Column(nullable = false, length = 8)
    @Enumerated(EnumType.STRING)
    private State state;

    @Column(nullable = true, length = 20)
    private String nickname;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Location> locations;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<UserDevice> devices;

    @CreationTimestamp
    private Timestamp createdTime;

    @UpdateTimestamp
    private Timestamp updatedTime;

    @Nullable
    @ManyToOne
    private MeasuredUnit measuredUnit;

    public void updateMeasuredUnit(MeasuredUnit updatedMeasuredUnit){
        measuredUnit = updatedMeasuredUnit;
    }

    public void setUsername(String username)
    {
        if(!Pattern.matches("^[\\w_]{5,20}$",username))
        {
            throw new IllegalArgumentException();
        }
        this.username = username;
    }

    public void setPassword(String password)
    {
        if(!Pattern.matches("^.*(?=^.{8,20}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$",password))
        {
            throw new IllegalArgumentException();
        }
        this.password = password;
    }

    public void setEmail(String email)
    {
        if(!Pattern.matches("^[\\da-zA-Z]([-_.]?[\\da-zA-Z])*@[\\da-zA-Z]([-_.]?[\\da-zA-Z])*.[a-zA-Z]{2,3}$",email))
        {
            throw new IllegalArgumentException();
        }
        this.email = email;
    }

    public void setRole(String role)
    {
        try
        {
            this.role = Role.valueOf(role);
        }
        catch (IllegalArgumentException e)
        {
            throw new IllegalArgumentException();
        }
    }

    public void setState(State state)
    {
        this.state = state;
    }

    @Override
    public String toString() {
        return "DataCompilation{" +"owner_id=" + id +
                "username=" + username +
                "role=" + role +
                '}';
    }
}
