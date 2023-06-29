package com.example.demo.user.service;

import com.example.demo.exceptions.CustomMailException;
import com.example.demo.exceptions.DuplicateAttributeException;
import com.example.demo.jwt.util.JwtUtil;
import com.example.demo.mail.service.MailService;
import com.example.demo.redis.entity.AuthNum;
import com.example.demo.redis.repo.AuthNumRepository;
import com.example.demo.user.dto.request.EmailDTO;
import com.example.demo.user.dto.request.RegisterDTO;
import com.example.demo.user.dto.request.StudentAddDTO;
import com.example.demo.user.dto.response.Student_EducatorDTO;
import com.example.demo.user.model.entity.Educator;
import com.example.demo.user.model.entity.Student;
import com.example.demo.user.model.entity.Student_Educator;
import com.example.demo.user.model.entity.User;
import com.example.demo.user.model.enumerate.State;
import com.example.demo.user.repository.EducatorRepository;
import com.example.demo.user.repository.StudentRepository;
import com.example.demo.user.repository.Student_EducatorRepository;
import com.example.demo.user.repository.UserRepository;
import com.example.demo.user.util.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MailService mailService;
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final EducatorRepository educatorRepository;
    private final AuthNumRepository authNumRepository;
    private final Student_EducatorRepository student_educatorRepository;

    /**
     register service
     */
    @Transactional
    public void addUser(RegisterDTO registerDTO) {
        User user = registerDTO.getRole().generateUserByRole(registerDTO, bCryptPasswordEncoder);
        if(userRepository.existsByUsernameAndState(user.getUsername(), State.ACTIVE)) {
            throw new DuplicateAttributeException("아이디");
        }
        userRepository.save(user);
    }

    @Transactional(rollbackFor = {MessagingException.class, MailException.class})
    public void sendAuthNum(EmailDTO emailDTO) throws CustomMailException, DuplicateAttributeException
    {
        if(userRepository.existsByEmailAndState(emailDTO.getEmail(), State.ACTIVE)) {
            throw new DuplicateAttributeException("이메일");
        }
        String authNum = Utils.generateAuthNum();
        mailService.sendAuthMail(emailDTO, authNum);
        authNumRepository.save(AuthNum.of(emailDTO.getEmail(), authNum));
    }

    @Transactional
    public void checkAuthNum(String email, String authNum) {
        AuthNum auth = authNumRepository.findById(email).orElseThrow(()->new IllegalArgumentException("인증정보가 틀렸습니다"));
        authNumRepository.deleteById(email);
        if(!auth.getAuthNum().equals(authNum)) {
            throw new IllegalArgumentException("인증정보가 틀렸습니다");
        }
    }

    @Transactional
    public void addStudent(Map<String, Object> userInfo, StudentAddDTO studentAddDTO) {
        String educatorUsername = userInfo.get(JwtUtil.claimUsername).toString();

        Educator educator = educatorRepository.findByUsername(educatorUsername).orElseThrow(()->new IllegalArgumentException("해당 교사가 존재하지 않습니다"));
        List<Student> students = studentRepository.findAllByUsernameIn(studentAddDTO.getStudentUsernames());

        Map<String, List<Student>> studentsAlreadyExist = student_educatorRepository.findAllByEducator(educator).stream()
                .map(Student_Educator::getStudent)
                .collect(Collectors.groupingBy(User::getUsername));

        List<Student_Educator> elemToAdd = students.stream()
                .filter(student -> !studentsAlreadyExist.containsKey(student.getUsername()))
                .map(student -> Student_Educator.of(student, educator))
                .collect(Collectors.toList());

        student_educatorRepository.saveAll(elemToAdd);
    }

    @Transactional(readOnly = true)
    public Student_EducatorDTO getEducatingStudents(Map<String, Object> userInfo) {
        String educatorUsername = userInfo.get(JwtUtil.claimUsername).toString();
        Educator educator = educatorRepository.findByUsername(educatorUsername).orElseThrow(()->new IllegalArgumentException("해당 교사가 존재하지 않습니다"));
        List<Student> students = student_educatorRepository.findAllByEducator(educator).stream()
                .map(Student_Educator::getStudent)
                .collect(Collectors.toList());
        return new Student_EducatorDTO(educator, students);
    }

    @Transactional
    public Student_Educator findEducatorByStudent(Student student) {
        return student_educatorRepository.findByStudent(student);
    }

    @Transactional
    public List<Student_Educator> findAllByEducator(Educator educator) {
        return student_educatorRepository.findAllByEducator(educator);
    }
}
