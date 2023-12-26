package com.example.demo.datacontrol.datachart.domain.entity;

import com.example.demo.user.model.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class CustomDataChart {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User owner;
    private Long classId;
    private Long chapterId;
    private Long sequenceId;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "x_properties_id")
    private CustomDataChartProperties x;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "y1_properties_id")
    private CustomDataChartProperties y1;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "y2_properties_id")
    private CustomDataChartProperties y2;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "z_properties_id")
    private CustomDataChartProperties z;

    public void updateOwner(User owner){
        this.owner = owner;
    }
}
