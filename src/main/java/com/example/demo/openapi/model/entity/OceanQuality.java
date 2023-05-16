package com.example.demo.openapi.model.entity;

import com.example.demo.openapi.model.parent.AirQualityParent;
import com.example.demo.openapi.model.parent.OceanQualityParent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class OceanQuality extends OceanQualityParent {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
