package com.example.demo.device.service;

import com.example.demo.DTO.MacListDTO;
import com.example.demo.device.model.UserDevice;
import com.example.demo.device.repository.UserDeviceRepository;
import com.example.demo.user.model.entity.User;
import com.example.demo.user.model.enumerate.State;
import com.example.demo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class UserDeviceService {
    private final UserDeviceRepository userDeviceRepository;
    private final UserRepository userRepository;
    @Transactional
    public void addDevice(UserDevice userDevice)
    {
        userDeviceRepository.save(userDevice);
    }
    @Transactional
    public void registerDevice(String username, String deviceMAC)
    {
        UserDevice userDevice = userDeviceRepository.findByMac(deviceMAC).orElseThrow(IllegalArgumentException::new);
        userDevice.setUser(userRepository.findByUsernameAndState(username, State.ACTIVE).orElseThrow(IllegalArgumentException::new));
    }
    @Transactional(readOnly = true)
    public List<MacListDTO> getDeviceList(String username)
    {
        User user = userRepository.findByUsernameAndState(username, State.ACTIVE).orElseThrow(()->{throw new IllegalArgumentException();});
        Map<String,List<String>> map = new HashMap<>();
        List<MacListDTO> list = new ArrayList<>();

//        if(!user.getRole().equals(Role.ROLE_STUDENT))
//        {
//            Educator educator = (Educator) user;
//            MacListDTO macListDTO = new MacListDTO(educator.getUsername(), new ArrayList<>());
//            educator.getDevices().forEach((elem)->{
//                macListDTO.getMacList().add(elem.getMac());
//            });
//            list.add(macListDTO);
//
//            List<UserDevice> deviceList = userDeviceRepository.findAllByUserIn(new ArrayList<>(educator.getStudents()));
//            deviceList.forEach((elem)->{
//                map.put(elem.getUser().getUsername(), new ArrayList<>());
//            });
//            deviceList.forEach((elem)-> {
//                map.get(elem.getUser().getUsername()).add(elem.getUserDeviceMAC());
//            });
//            map.forEach((name, elem)->{
//                MacListDTO macListDTO1 = new MacListDTO(name, elem);
//                list.add(macListDTO1);
//            });
//        }
//        else
//        {
//            MacListDTO macListDTO = new MacListDTO(user.getUsername(), new ArrayList<>());
//            List<UserDevice> deviceList = userDeviceRepository.findAllByUser(user);
//            deviceList.forEach((elem)->{
//                macListDTO.getMacList().add(elem.getUserDeviceMAC());
//            });
//            list.add(macListDTO);
//        }

        return list;
    }
}
