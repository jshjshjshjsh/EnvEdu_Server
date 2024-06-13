package com.example.demo.datacontrol.datachunk.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class MeasuredUnit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Nullable
    private String unit;
    @Nullable
    private String classNumber;
    @Nullable
    private LocalDateTime joinDateTime;

    public void updateJoinDateTime(){
        joinDateTime = LocalDateTime.now();
    }

    public MeasuredUnit(@Nullable String unit, @Nullable String classNumber, @Nullable LocalDateTime joinDateTime) {
        this.unit = unit;
        this.classNumber = classNumber;
        this.joinDateTime = joinDateTime;
    }
}
