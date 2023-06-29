package com.example.demo.device.service;

import com.example.demo.device.dto.request.AddMACDTO;
import com.example.demo.device.dto.request.DeviceUpdateDTO;
import com.example.demo.device.dto.response.DeviceListDTO;
import com.example.demo.device.dto.response.RelatedUserDeviceListDTO;
import com.example.demo.device.model.UserDevice;
import com.example.demo.device.repository.UserDeviceRepository;
import com.example.demo.user.model.entity.Educator;
import com.example.demo.user.model.entity.Student_Educator;
import com.example.demo.user.model.entity.User;
import com.example.demo.user.model.enumerate.State;
import com.example.demo.user.repository.Student_EducatorRepository;
import com.example.demo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@RequiredArgsConstructor
@Service
public class UserDeviceService {
    private final UserDeviceRepository userDeviceRepository;

    private final UserRepository userRepository;

    private final Student_EducatorRepository student_educatorRepository;


    @Transactional
    public void addDevice(AddMACDTO addMACDTO) {
        List<UserDevice> devices = new ArrayList<>();
        for(String mac : addMACDTO.getMacs()) {
            UserDevice device = UserDevice.of(mac);
            devices.add(device);
        }
        userDeviceRepository.saveAll(devices);
    }

    @Transactional(readOnly = true)
    public DeviceListDTO getAllDevices() {
        List<UserDevice> devices = userDeviceRepository.findAll();
        return new DeviceListDTO(devices);
    }

    @Transactional
    public void updateDevice(DeviceUpdateDTO deviceUpdateDTO) {
        boolean reset = deviceUpdateDTO.isReset();
        UserDevice userDevice = userDeviceRepository.findByMac(deviceUpdateDTO.getMac()).orElseThrow(()->new IllegalArgumentException("해당 기기가 존재하지 않습니다"));

        if(reset) {
            userDevice.updateUser(null);
            userDevice.updateName(null);
        }
        if(!reset) {
            User user = userRepository.findByUsernameAndState(deviceUpdateDTO.getUsername(), State.ACTIVE).orElseThrow(()->new IllegalArgumentException("해당 유저가 존재하지 않습니다"));
            userDevice.updateUser(user);
            userDevice.updateName(deviceUpdateDTO.getDeviceName());
        }
    }

    @Transactional
    public void deleteDevice(String mac) {
        userDeviceRepository.deleteByMac(mac);
    }

    @Transactional(readOnly = true)
    public RelatedUserDeviceListDTO getDeviceList(String username) {
        User user = userRepository.findByUsernameAndState(username, State.ACTIVE).orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다"));
        List<User> allRelatedUsers = new ArrayList<>();
        allRelatedUsers.add(user);

        if(user instanceof Educator) {
            List<Student_Educator> relatedUsers = student_educatorRepository.findAllByEducator((Educator) user);
            for (Student_Educator student_educator : relatedUsers) {
                allRelatedUsers.add(student_educator.getStudent());
            }
        }

        List<UserDevice> allDevices = userDeviceRepository.findAllByUserIn(allRelatedUsers);
        return new RelatedUserDeviceListDTO(allDevices);
    }
}
