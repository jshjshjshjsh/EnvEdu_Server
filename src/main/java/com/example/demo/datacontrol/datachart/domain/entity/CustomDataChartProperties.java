package com.example.demo.datacontrol.datachart.domain.entity;

import com.example.demo.datacontrol.datachart.domain.types.Axis;
import com.example.demo.datacontrol.datachart.domain.types.AxisType;
import com.example.demo.datacontrol.datachart.domain.types.ChartType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class CustomDataChartProperties {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private Axis axis;
    private String axisName;
    @Enumerated(EnumType.STRING)
    private AxisType axisType;
    private Float minimumValue;
    private Float maximumValue;
    private Float stepSize;
    @Enumerated(EnumType.STRING)
    private ChartType chartType;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private CustomDataChart customDataChart;

    public void updateCustomDataChart(CustomDataChart customDataChart) {
        this.customDataChart = customDataChart;
    }
}
