package com.example.demo.openapi.model.entity;

import com.example.demo.openapi.model.parent.OceanQualityParent;
import com.example.demo.user.model.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "OceanQuality"
        , uniqueConstraints = @UniqueConstraint(columnNames = {"PTNM", "WMYR", "WMOD"}))
public class OceanQuality extends OceanQualityParent {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User owner;

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
