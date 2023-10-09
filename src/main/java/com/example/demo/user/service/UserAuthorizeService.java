package com.example.demo.user.service;

import com.example.demo.user.dto.request.EducatorAuthenticationDTO;
import com.example.demo.user.dto.response.UserDto;
import com.example.demo.user.model.entity.Educator;
import com.example.demo.user.model.entity.User;
import com.example.demo.user.model.enumerate.IsAuthorized;
import com.example.demo.user.repository.EducatorRepository;
import com.example.demo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserAuthorizeService {
    private final EducatorRepository educatorRepository;
    private final UserRepository userRepository;

    @Transactional
    public void changeEducatorStatus(EducatorAuthenticationDTO educatorAuthenticationDTO){
        Optional<Educator> educator = educatorRepository.findById(educatorAuthenticationDTO.getId());
        educator.get().updateAuthorization(educatorAuthenticationDTO.getIsAuthorized());
    }

    public List<UserDto> findUser(){
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(UserDto::new)
                .collect(Collectors.toList());
    }

    public List<UserDto> findEducatorByNoAuthorized() {
        Optional<List<Educator>> unauthorizedEducators = educatorRepository.findIdByIsAuthorized(IsAuthorized.NO);

        if (unauthorizedEducators.isEmpty()) {
            return Collections.emptyList();
        }

        List<Long> unauthorizedEducatorIds = unauthorizedEducators.get().stream()
                .map(Educator::getId)
                .collect(Collectors.toList());

        List<User> users = userRepository.findByIdIn(unauthorizedEducatorIds).get();

        return users.stream()
                .map(UserDto::new)
                .collect(Collectors.toList());
    }

}
