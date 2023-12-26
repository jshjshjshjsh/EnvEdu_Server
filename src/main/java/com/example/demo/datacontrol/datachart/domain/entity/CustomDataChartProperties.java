package com.example.demo.datacontrol.datachart.domain.entity;

import com.example.demo.datacontrol.datachart.domain.types.AxisType;
import com.example.demo.datacontrol.datachart.domain.types.ChartLabelPosition;
import com.example.demo.datacontrol.datachart.domain.types.ChartLegendPosition;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class CustomDataChartProperties {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String axisName;
    @Enumerated(EnumType.STRING)
    private AxisType axisType;
    private Float minimumValue;
    private Float maximumValue;
    private Float stepSize;
    @Enumerated(EnumType.STRING)
    private ChartLegendPosition legendPosition;
    @Enumerated(EnumType.STRING)
    private ChartLabelPosition labelPosition;
}
