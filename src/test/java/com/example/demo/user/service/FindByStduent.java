package com.example.demo.user.service;

import com.example.demo.jpa.device.dto.response.RelatedUserDeviceListDTO;
import com.example.demo.jpa.device.service.UserDeviceService;
import com.example.demo.jpa.user.model.entity.Student;
import com.example.demo.jpa.user.model.entity.Student_Educator;
import com.example.demo.jpa.user.model.entity.User;
import com.example.demo.jpa.user.repository.UserRepository;
import com.example.demo.jpa.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class FindByStduent {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserDeviceService userDeviceService;

    @Test
    public void FindBySingleStduent() {

        Optional<User> student1 = userRepository.findByUsername("Student1");
        Student_Educator educatorByStudent = userService.findEducatorByStudent((Student) student1.get());
        List<Student_Educator> allByEducator = userService.findAllByEducator(educatorByStudent.getEducator());
        List<RelatedUserDeviceListDTO> deviceLists = new ArrayList<>();
        for (Student_Educator s :
                allByEducator) {
            deviceLists.add(userDeviceService.getDeviceList(s.getStudent().getUsername()));
        }


        System.out.println("educatorByStudent.toString( = " + educatorByStudent.toString());
    }
}
