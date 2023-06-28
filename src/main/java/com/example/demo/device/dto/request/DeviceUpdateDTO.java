package com.example.demo.device.dto.request;

import lombok.Getter;

@Getter
public class DeviceUpdateDTO {
    private String mac;
    private String username;
    private String deviceName;
    /**
     * reset == true이면 해당 기기에 등록된 사용자 정보 및 별칭 초기화
     */
    private boolean reset;
}
