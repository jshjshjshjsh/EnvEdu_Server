package com.example.demo.datacontrol.dataclassroom.service;

import com.example.demo.datacontrol.dataclassroom.domain.entity.ClassroomClass;
import com.example.demo.datacontrol.dataclassroom.repository.ClassroomClassRepository;
import com.example.demo.user.model.entity.Educator;
import com.example.demo.user.model.entity.Student_Educator;
import com.example.demo.user.model.entity.User;
import com.example.demo.user.repository.UserRepository;
import com.example.demo.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClassroomService {

    private final ClassroomClassRepository classroomRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<ClassroomClass> findAllClassroom(String username){
//        Optional<User> user = userRepository.findByUsername(username);
        Educator educator = userService.findStudentsByStudentOrEducator(username).get(0).getEducator();

        return classroomRepository.findAllByOwner(educator);
    }
}
