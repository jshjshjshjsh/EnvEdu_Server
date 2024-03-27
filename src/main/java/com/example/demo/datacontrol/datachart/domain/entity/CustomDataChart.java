package com.example.demo.datacontrol.datachart.domain.entity;

import com.example.demo.datacontrol.datachart.domain.types.ChartLabelPosition;
import com.example.demo.datacontrol.datachart.domain.types.ChartLegendPosition;
import com.example.demo.datacontrol.datachart.domain.types.ChartType;
import com.example.demo.user.model.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@EntityListeners(CustomDataChartListener.class)
@NoArgsConstructor
public class CustomDataChart {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Enumerated(EnumType.STRING)
    private ChartLegendPosition legendPosition;
    @Enumerated(EnumType.STRING)
    private ChartLabelPosition labelPosition;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User owner;
    @Transient
    private String username;
    private Long classId;
    private Long chapterId;
    private Long sequenceId;
    private Boolean canShare = false;
    private Boolean canSubmit = false;
    @Enumerated(EnumType.STRING)
    private ChartType chartType;
    @Column(columnDefinition = "BINARY(16)")
    private UUID uuid;
    private Boolean forClass;
    @OneToMany(mappedBy = "customDataChart", cascade = CascadeType.ALL)
    private List<CustomDataChartProperties> axisProperties = new ArrayList<>();
    @Transient
    private String properties;
    @Transient
    private String data;

    public void updateOwner(User owner){
        this.owner = owner;
    }

    public void updateUsername() {
        this.username = owner.getUsername();
    }

    public void updateUuid(UUID uuid){
        this.uuid = uuid;
    }

    public void updateClassroomIds(Long classId, Long chapterId, Long sequenceId) {
        this.classId = classId;
        this.chapterId = chapterId;
        this.sequenceId = sequenceId;
    }

    public void updateShareAndSubmit(Boolean canShare, Boolean canSubmit){
        this.canShare = canShare;
        this.canSubmit = canSubmit;
    }

    public CustomDataChart(String title, ChartLegendPosition legendPosition, ChartLabelPosition labelPosition, User owner, String username, Long classId, Long chapterId, Long sequenceId, ChartType chartType, UUID uuid, Boolean forClass, List<CustomDataChartProperties> axisProperties, Boolean canShare, Boolean canSubmit) {
        this.title = title;
        this.legendPosition = legendPosition;
        this.labelPosition = labelPosition;
        this.owner = owner;
        this.username = username;
        this.classId = classId;
        this.chapterId = chapterId;
        this.sequenceId = sequenceId;
        this.chartType = chartType;
        this.uuid = uuid;
        this.forClass = forClass;
        this.axisProperties = axisProperties;
        this.canShare = canShare;
        this.canSubmit = canSubmit;
    }
}
