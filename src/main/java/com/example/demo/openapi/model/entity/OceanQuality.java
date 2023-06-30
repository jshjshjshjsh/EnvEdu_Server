package com.example.demo.openapi.model.entity;

import com.example.demo.openapi.model.parent.OceanQualityParent;
import com.example.demo.user.model.entity.User;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "OceanQuality"
        , uniqueConstraints = @UniqueConstraint(columnNames = {"PTNM"}))
public class OceanQuality extends OceanQualityParent {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    //@NotNull
    private User owner;

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
