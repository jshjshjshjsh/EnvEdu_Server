package com.example.demo.user.service;

import com.example.demo.datacontrol.datachunk.model.MeasuredUnit;
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
import com.example.demo.user.model.entity.*;
import com.example.demo.user.model.enumerate.State;
import com.example.demo.user.repository.*;
import com.example.demo.user.util.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
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
    private final InviteCodeRepository inviteCodeRepository;

    @Transactional
    public Boolean joinInviteCode(String username, String inviteCode){
        Optional<User> findUser = userRepository.findByUsername(username);
        Optional<InviteCode> findInviteCode = inviteCodeRepository.findByCode(inviteCode);
        if (findInviteCode.isPresent() && findUser.isPresent()){
            student_educatorRepository.save(Student_Educator.of((Student) findUser.get(), (Educator) findInviteCode.get().getUser()));

            return true;
        }

        return false;
    }

    @Transactional
    public InviteCode generateInviteCode(String educator){
        Optional<User> findUser = userRepository.findByUsername(educator);
        Optional<InviteCode> findInviteCode = inviteCodeRepository.findByUser(findUser.get());

        String characters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random = new Random();
        StringBuilder randomBuilder = new StringBuilder(6);

        for (int i = 0; i < 6; i++) {
            int randomIndex = random.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            randomBuilder.append(randomChar);
        }
        LocalDateTime now = LocalDateTime.now();

        if(findInviteCode.isPresent()){
            findInviteCode.get().updateInviteCode(randomBuilder.toString(), now, now.plusHours(1L));
            return findInviteCode.get();
        }
        else{
            InviteCode inviteCode = new InviteCode(findUser.get(), randomBuilder.toString(), now, now.plusHours(1L));
            inviteCodeRepository.save(inviteCode);
            return inviteCode;
        }

    }

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

    public List<Student_Educator> findStudentsByStudentOrEducator(String username){
        User user = userRepository.findByUsername(username).get();

        if (user instanceof Student){
            Student_Educator educatorByStudent = findEducatorByStudent((Student) user);
            user = educatorByStudent.getEducator();
        }
        return findAllByEducator((Educator) user);
    }

    @Transactional
    public Student_Educator findEducatorByStudent(Student student) {
        return student_educatorRepository.findByStudent(student);
    }

    @Transactional
    public List<Student_Educator> findAllByEducator(Educator educator) {
        return student_educatorRepository.findAllByEducator(educator);
    }

    @Transactional
    public void updateMeasuredUnit(String target, MeasuredUnit measuredUnit){
        Optional<User> findUser = userRepository.findByUsername(target);
        findUser.ifPresent(user -> user.updateMeasuredUnit(measuredUnit));
    }

    @Transactional
    public void updateAllMeasuredUnit(String target, MeasuredUnit measuredUnit){
        Optional<User> findUser = userRepository.findByUsername(target);

        if(findUser.isPresent() && findUser.get() instanceof Educator){
            findUser.get().updateMeasuredUnit(measuredUnit);
            List<Student_Educator> allByEducator = findAllByEducator((Educator) findUser.get());

            for (Student_Educator student : allByEducator) {
                userRepository.findByUsername(student.getStudent().getUsername()).ifPresent(user -> user.updateMeasuredUnit(measuredUnit));
            }
        }
    }
}
