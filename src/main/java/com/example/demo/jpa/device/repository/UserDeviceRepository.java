package com.example.demo.jpa.device.repository;

import com.example.demo.jpa.device.model.UserDevice;
import com.example.demo.jpa.user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserDeviceRepository extends JpaRepository<UserDevice, Long> {
    Optional<UserDevice> findByMac(String userDeviceMAC);
    boolean existsByMac(String userDeviceMAC);
    List<UserDevice> findAllByUser(User user);
    List<UserDevice> findAllByUserIn(List<User> list);
    void deleteByMac(String userDeviceMAC);
    UserDevice findByName(String name);
}
