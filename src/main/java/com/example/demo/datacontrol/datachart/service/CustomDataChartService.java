package com.example.demo.datacontrol.datachart.service;

import com.example.demo.datacontrol.datachart.domain.entity.CustomDataChart;
import com.example.demo.datacontrol.datachart.domain.entity.CustomDataChartProperties;
import com.example.demo.datacontrol.datachart.repository.CustomDataChartRepository;
import com.example.demo.datacontrol.dataliteracy.model.entity.CustomData;
import com.example.demo.datacontrol.dataliteracy.repository.CustomDataRepository;
import com.example.demo.user.model.entity.Student_Educator;
import com.example.demo.user.model.entity.User;
import com.example.demo.user.repository.UserRepository;
import com.example.demo.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomDataChartService {

    private final UserService userService;
    private final UserRepository userRepository;
    private final CustomDataChartRepository customDataChartRepository;
    private final CustomDataRepository customDataRepository;

    @Transactional
    public CustomDataChart createCustomDataChart(CustomDataChart customDataChart, String username){
        Optional<User> user = userRepository.findByUsername(username);

        Optional<CustomDataChart> findCustomDataChart = customDataChartRepository.findByClassIdAndChapterIdAndSequenceIdAndOwnerAndTitle(
            customDataChart.getClassId(), customDataChart.getChapterId(), customDataChart.getSequenceId(), user.get(),customDataChart.getTitle());

        findCustomDataChart.ifPresent(dataChart -> customDataChartRepository.deleteById(dataChart.getId()));

        customDataChart.updateOwner(user.get());
        List<CustomData> customDataList = customDataRepository.findCustomDataByDataUUID(customDataChart.getUuid()).get();
        for (CustomData customData : customDataList) {
            customData.updateIsSubmit(true);
        }
        for (CustomDataChartProperties properties : customDataChart.getAxisProperties()) {
            properties.updateCustomDataChart(customDataChart);
        }

        customDataChartRepository.save(customDataChart);
        return customDataChart;
    }

    @Transactional(readOnly = true)
    public List<CustomDataChart> getRelateCustomDataChart(Long classId, Long chapterId, Long sequenceId, String username){
        List<Student_Educator> students = userService.findStudentsByStudentOrEducator(username);

        List<CustomDataChart> result = new ArrayList<>();
        for (Student_Educator s_e: students){
            Optional<CustomDataChart> find = customDataChartRepository.findByClassIdAndChapterIdAndSequenceIdAndOwner(
                    classId, chapterId, sequenceId, s_e.getStudent());
            find.ifPresent(result::add);
        }

        return result;
    }

    @Transactional(readOnly = true)
    public List<CustomDataChart> getAllCustomDataChartNotForClass(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return customDataChartRepository.findAllByClassIdAndChapterIdAndSequenceIdAndOwner(0L, 0L, 0L, user.get());
    }


    @Transactional(readOnly = true)
    public CustomDataChart getSingleCustomDataChart(Long classId, Long chapterId, Long sequenceId, String username){
        Optional<User> user = userRepository.findByUsername(username);
        return user.map(value -> customDataChartRepository.findByClassIdAndChapterIdAndSequenceIdAndOwner(classId, chapterId, sequenceId, value).get()).orElse(null);
    }
}
