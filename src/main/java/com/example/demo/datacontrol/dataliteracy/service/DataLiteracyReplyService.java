package com.example.demo.datacontrol.dataliteracy.service;

import com.example.demo.datacontrol.dataliteracy.model.dto.CustomDataReplyDto;
import com.example.demo.datacontrol.dataliteracy.model.entity.CustomDataReply;
import com.example.demo.datacontrol.dataliteracy.repository.CustomDataReplyRepository;
import com.example.demo.user.model.entity.Educator;
import com.example.demo.user.model.entity.Student;
import com.example.demo.user.model.entity.Student_Educator;
import com.example.demo.user.model.entity.User;
import com.example.demo.user.repository.UserRepository;
import com.example.demo.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class DataLiteracyReplyService {

    private final UserService userService;
    private final UserRepository userRepository;
    private final CustomDataReplyRepository customDataReplyRepository;

    @Transactional(readOnly = true)
    public List<CustomDataReply> getCustomDataReply(Long classId,Long  chapterId,Long  sequenceId, String username){
        User user = userRepository.findByUsername(username).get();

        if (user instanceof Student){
            Student_Educator educatorByStudent = userService.findEducatorByStudent((Student) user);
            user = educatorByStudent.getEducator();
        }
        List<Student_Educator> students = userService.findAllByEducator((Educator) user);
        List<CustomDataReply> result = new ArrayList<>();

        // 교사의 댓글
        List<CustomDataReply> findByEducator = customDataReplyRepository.findAllByClassIdAndChapterIdAndSequenceIdAndOwner(
                classId, chapterId, sequenceId, students.get(0).getEducator());
        result.addAll(findByEducator);

        // 교사에게 소속된 학생들의 댓글
        for (Student_Educator s_e: students){
            List<CustomDataReply> find = customDataReplyRepository.findAllByClassIdAndChapterIdAndSequenceIdAndOwner(
                    classId, chapterId, sequenceId, s_e.getStudent());
            result.addAll(find);
        }

        // 생성날짜 내림차순, reply id 오름차순 정렬
        Collections.sort(result, Comparator.comparing(CustomDataReply::getLatestTime).reversed()
                .thenComparing(CustomDataReply::getId));

        return result;
    }

    @Transactional
    public Boolean deleteCustomDataReply(CustomDataReply customDataReply, String username) {
        Optional<User> user = userRepository.findByUsername(username);
        Optional<CustomDataReply> findCustomDataReply = customDataReplyRepository.findByIdAndOwner(customDataReply.getId(), user.get());
        if (findCustomDataReply.isPresent() && findCustomDataReply.get().getOwner().getId() == user.get().getId()) {
            customDataReplyRepository.delete(findCustomDataReply.get());
            return true;
        }
        return false;
    }

    // Reply 조회, 등록, 수정, 삭제
    @Transactional
    public void createCustomDataReply(CustomDataReplyDto customDataReplyDto, String username){
        Optional<User> user = userRepository.findByUsername(username);
        customDataReplyDto.updateOwner(user.orElse(null));
        customDataReplyRepository.save(customDataReplyDto.toEntity());
    }

    @Transactional
    public void updateCustomDataReply(CustomDataReply customDataReply, String username){
        Optional<User> user = userRepository.findByUsername(username);
        Optional<CustomDataReply> findCustomDataReply = customDataReplyRepository.findByIdAndOwner(customDataReply.getId(), user.get());
        findCustomDataReply.ifPresent(dataReply -> dataReply.updateTitleAndContent(customDataReply.getTitle(), customDataReply.getTitle()));
    }

}
