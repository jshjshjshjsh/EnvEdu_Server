package com.example.demo.datacontrol.datachart.service;

import com.example.demo.datacontrol.datachart.domain.entity.CustomDataChart;
import com.example.demo.datacontrol.datachart.repository.CustomDataChartRepository;
import com.example.demo.user.model.entity.User;
import com.example.demo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomDataChartService {

    private final UserRepository userRepository;
    private final CustomDataChartRepository customDataChartRepository;

    @Transactional
    public void createCustomDataChart(CustomDataChart customDataChart, String username){
        Optional<User> user = userRepository.findByUsername(username);

        user.ifPresent(customDataChart::updateOwner);
        customDataChartRepository.save(customDataChart);
    }

    @Transactional(readOnly = true)
    public CustomDataChart getSingleCustomDataChart(Long classId, Long chapterId, Long sequenceId, String username){
        Optional<User> user = userRepository.findByUsername(username);
        return user.map(value -> customDataChartRepository.findByClassIdAndChapterIdAndSequenceIdAndOwner(classId, chapterId, sequenceId, value).get()).orElse(null);
    }
}
