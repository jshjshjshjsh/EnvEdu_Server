package com.example.demo.datacontrol.dataliteracy.model.dto;

import com.example.demo.datacontrol.dataliteracy.model.entity.CustomData;

import javax.persistence.PostLoad;

/* 객체 select 해온 시점에 username을 주입 해주는 기능 */
public class CustomDataListener {
    @PostLoad
    public void prePersist(CustomData customData) {
        customData.updateUsername();
    }
}
