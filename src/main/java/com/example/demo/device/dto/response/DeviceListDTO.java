package com.example.demo.device.dto.response;

import com.example.demo.device.model.UserDevice;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class DeviceListDTO {
    private final List<DeviceListElement> list;

    public DeviceListDTO(List<UserDevice> devices) {
        List<DeviceListElement> list = new ArrayList<>();
        for(UserDevice device : devices) {
            DeviceListElement element = new DeviceListElement(device);
            list.add(element);
        }
        this.list = list;
    }

    @Getter
    private static class DeviceListElement {
        private final String mac;
        private final String name;
        private final String username;

        private DeviceListElement(UserDevice userDevice) {
            this.mac = userDevice.getMac();
            this.name = userDevice.getName() == null ? "" : userDevice.getName();
            this.username = userDevice.getUser() == null ? "" : userDevice.getUser().getUsername();
        }
    }
}
