package com.example.demo.jpa.datacontrol.datachart.domain.entity;

import javax.persistence.PostLoad;

/* 객체 select 해온 시점에 username을 주입 해주는 기능 */
public class CustomDataChartListener {
    @PostLoad
    public void prePersist(CustomDataChart customDataChart) {
        customDataChart.updateUsername();
    }
}
