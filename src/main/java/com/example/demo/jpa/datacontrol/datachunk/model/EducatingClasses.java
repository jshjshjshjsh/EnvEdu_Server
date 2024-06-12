package com.example.demo.jpa.datacontrol.datachunk.model;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
public class EducatingClasses {
    @Id @GeneratedValue
    private Long id;
    private String className;
}
