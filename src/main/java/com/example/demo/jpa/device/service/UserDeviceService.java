package com.example.demo.jpa.device.service;

import com.example.demo.jpa.device.dto.request.AddMACDTO;
import com.example.demo.jpa.device.dto.request.DeviceUpdateDTO;
import com.example.demo.jpa.device.dto.response.DeviceListDTO;
import com.example.demo.jpa.device.dto.response.RelatedUserDeviceListDTO;
import com.example.demo.jpa.device.model.UserDevice;
import com.example.demo.jpa.device.repository.UserDeviceRepository;
import com.example.demo.jpa.user.model.entity.Educator;
import com.example.demo.jpa.user.model.entity.Student_Educator;
import com.example.demo.jpa.user.model.entity.User;
import com.example.demo.jpa.user.model.enumerate.State;
import com.example.demo.jpa.user.repository.Student_EducatorRepository;
import com.example.demo.jpa.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

@RequiredArgsConstructor
@Service
public class UserDeviceService {
    private final UserDeviceRepository userDeviceRepository;

    private final UserRepository userRepository;

    private final Student_EducatorRepository student_educatorRepository;


    @Transactional
    public void addDevice(AddMACDTO addMACDTO, List<User> users) {
        List<UserDevice> devices = new ArrayList<>();
        int i=0;
        for(String mac : addMACDTO.getMacs()) {
            UserDevice device = UserDevice.of(mac);
            device.updateUser(users.get(i));
            devices.add(device);
            i++;
        }
        userDeviceRepository.saveAll(devices);
    }
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


    /**
     * 해당 메서드는 현재 접속한 사용자(강사)와 강사에 소속된 학생들의 디바이스의 리스트를 리턴해주는 메서드입니다
     * @param username
     * @author 김선규
     */
    @Transactional(readOnly = true)
    public RelatedUserDeviceListDTO getDeviceList(String username) {
        User user = userRepository.findByUsernameAndState(username, State.ACTIVE).orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다"));

        // 유저 자신도 해당 관련된 디바이스의 사용자라고 추가해 준다.
        List<User> allRelatedUsers = new ArrayList<>();
        allRelatedUsers.add(user);

        // 유저 자신의 학생들의 디바이스도 추가해 준다.
        if(user instanceof Educator) {
            List<Student_Educator> relatedUsers = student_educatorRepository.findAllByEducator((Educator) user);
            for (Student_Educator student_educator : relatedUsers) {
                allRelatedUsers.add(student_educator.getStudent());
            }
        }

        // 추가해준 관련된 사람들을 바탕으로 관련된 디바이스의 내용을 가져온다.
        List<UserDevice> allDevices = userDeviceRepository.findAllByUserIn(allRelatedUsers);
        return new RelatedUserDeviceListDTO(allDevices);
    }


    /**
     * 해당 메서드는 디바이스가 커넥션 성공했을때 현재 실시간으로 연결되있는 부분을 저장해주는 메서드 입니다.
     * @param MAC
     * @author 김선규
     */
    @Transactional
    public boolean authenticateAndRegisterDevice(String MAC) {
        boolean exists = userDeviceRepository.existsByMac(MAC);
        if (exists) {
            Optional<UserDevice> userDevice = userDeviceRepository.findByMac(MAC);
            AtomicBoolean shouldSave = new AtomicBoolean(false);  // 저장 여부 결정을 위한 AtomicBoolean

            userDevice.ifPresent(device -> {
                if (!device.isDeviceOn()) {  // deviceOn이 false일 경우에만
                    device.setDeviceOn(true);  // deviceOn을 true로 설정
                    shouldSave.set(true);  // 저장해야 함을 표시
                }
            });

            if (shouldSave.get()) {
                userDevice.ifPresent(userDeviceRepository::save);  // 변경된 객체를 저장
                return true;
            } else {
                return false;  // 이미 deviceOn이 true인 경우, 변경 없이 false 반환
            }
        }
        return false;  // MAC 주소가 존재하지 않는 경우
    }

    /**
     * 해당 메서드는 디바이스의 연결이 해제 되었을때 해제됨을 저장하는 메서드입니다.
     * @param MAC
     * @author 김선규
     */
    @Transactional
    public boolean setDeviceOff(String MAC) {
        Optional<UserDevice> userDeviceOptional = userDeviceRepository.findByMac(MAC);

        // userDevice가 존재하면, deviceOn을 false로 설정하고 저장
        if (userDeviceOptional.isPresent()) {
            UserDevice userDevice = userDeviceOptional.get();
            userDevice.setDeviceOn(false);
            userDeviceRepository.save(userDevice);
            return true;
        }

        // Optional이 비어있는 경우, 적절한 처리를 할 수 있도록 false 반환하거나 예외를 발생시킬 수 있습니다.
        return false;
    }


}
