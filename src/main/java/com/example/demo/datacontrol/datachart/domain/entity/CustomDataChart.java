package com.example.demo.datacontrol.datachart.domain.entity;

import com.example.demo.datacontrol.datachart.domain.types.ChartLabelPosition;
import com.example.demo.datacontrol.datachart.domain.types.ChartLegendPosition;
import com.example.demo.datacontrol.datachart.domain.types.ChartType;
import com.example.demo.user.model.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@EntityListeners(CustomDataChartListener.class)
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
    @Enumerated(EnumType.STRING)
    private ChartType chartType;
    @Column(columnDefinition = "BINARY(16)")
    private UUID uuid;
    private Boolean forClass;
    @OneToMany(mappedBy = "customDataChart", cascade = CascadeType.ALL)
    private List<CustomDataChartProperties> axisProperties = new ArrayList<>();

    public void updateOwner(User owner){
        this.owner = owner;
    }

    public void updateUsername() {
        this.username = owner.getUsername();
    }
}
