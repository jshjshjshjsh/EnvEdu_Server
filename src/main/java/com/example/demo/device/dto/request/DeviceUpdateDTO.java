package com.example.demo.device.dto.request;

import lombok.Getter;

@Getter
public class DeviceUpdateDTO {
    private String mac;
    private String username;
    private String deviceName;
    private boolean reset;
}
