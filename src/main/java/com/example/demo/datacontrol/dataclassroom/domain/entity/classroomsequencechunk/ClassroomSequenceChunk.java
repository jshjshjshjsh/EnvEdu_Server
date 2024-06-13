package com.example.demo.datacontrol.dataclassroom.domain.entity.classroomsequencechunk;

import com.example.demo.datacontrol.datachart.domain.entity.CustomDataChart;
import com.example.demo.datacontrol.datachart.domain.entity.CustomDataChartProperties;
import com.example.demo.datacontrol.datachart.domain.types.ChartLabelPosition;
import com.example.demo.datacontrol.datachart.domain.types.ChartLegendPosition;
import com.example.demo.datacontrol.datachart.domain.types.ChartType;
import com.example.demo.datacontrol.dataclassroom.domain.entity.ClassroomSequence;
import com.example.demo.datacontrol.dataclassroom.domain.types.ClassroomSequenceType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
public class ClassroomSequenceChunk {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnore
    private ClassroomSequence classroomSequence;

    @Enumerated(EnumType.STRING)
    private ClassroomSequenceType classroomSequenceType;
    private Boolean studentVisibleStatus;
    private Boolean canSubmit;
    private Boolean canShare;

    // H1, H2, 토론, 질문, 차트
    private String title;
    private String content;

    // 사진, Youtube,
    private String url;

    // 표
    @Lob
    private String properties;
    @Lob
    private String data;

    // 차트
    @ManyToOne
    private CustomDataChart customDataChart;
    @Transient
    @Enumerated(EnumType.STRING)
    private ChartLegendPosition legendPosition;
    @Transient
    @Enumerated(EnumType.STRING)
    private ChartLabelPosition labelPosition;
    @Transient
    @Enumerated(EnumType.STRING)
    private ChartType chartType;
    @Transient
    @Column(columnDefinition = "BINARY(16)")
    private UUID uuid;
    @Transient
    private List<CustomDataChartProperties> axisProperties = new ArrayList<>();

    public void updateCustomDataChart(CustomDataChart customDataChart) {
        this.customDataChart = customDataChart;
    }

    public void deletePropertiesAndData(){
        this.properties = null;
        this.data = null;
    }

    public ClassroomSequenceChunk(ClassroomSequence classroomSequence, ClassroomSequenceType classroomSequenceType, Boolean studentVisibleStatus, String title, String content, String url, String properties, String data) {
        this.classroomSequence = classroomSequence;
        this.classroomSequenceType = classroomSequenceType;
        this.studentVisibleStatus = studentVisibleStatus;
        this.title = title;
        this.content = content;
        this.url = url;
        this.properties = properties;
        this.data = data;
    }

    public void updateClassroomSequence(ClassroomSequence classroomSequence){
        this.classroomSequence = classroomSequence;
    }
}
