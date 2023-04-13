package com.example.demo.user.service;

import com.example.demo.device.model.UserDevice;
import com.example.demo.device.repository.UserDeviceRepository;
import com.example.demo.exceptions.CustomMailException;
import com.example.demo.exceptions.DuplicateAttributeException;
import com.example.demo.redis.entity.AuthNum;
import com.example.demo.redis.repo.AuthNumRepository;
import com.example.demo.token.repository.RefreshTokenRepository;
import com.example.demo.user.dto.EmailDTO;
import com.example.demo.user.dto.RegisterDTO;
import com.example.demo.user.model.entity.Student;
import com.example.demo.user.model.entity.User;
import com.example.demo.user.model.enumerate.State;
import com.example.demo.user.repository.UserRepository;
import com.example.demo.user.util.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final AuthNumRepository authNumRepository;
    private final UserDeviceRepository userDeviceRepository;
    private final RefreshTokenRepository refreshTokenRepository;

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
        //mailService.sendAuthMail(emailDTO, authNum);
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


    @Transactional(readOnly = true)
    public boolean checkDuplicateUsernameAndEmail(String username, String email)
    {
        //return userRepository.existsByUsername(username) || userRepository.existsByEmailAndState(email, State.ACTIVE);
        return true;
    }
    @Transactional
    public void addUser(User user, AuthNum authNum)
    {
        userRepository.save(user);
        //registerAuthNumRepository.save(registerAuthNum);
        //mailService.sendAuthMail(user.getEmail(),registerAuthNum.getAuthNum());
    }

    @Transactional
    public void addStudent(String username, String studentUsername)
    {
        Student student = (Student) userRepository.findByUsernameAndState(studentUsername, State.ACTIVE).orElseThrow(()->{throw new IllegalArgumentException();});
        //student.setEducator((Educator) userRepository.findByUsernameAndIsActive(username, State.ACTIVE).orElseThrow(()->{throw new IllegalArgumentException();}));
    }
    @Transactional
    public void confirmAuthentication(String username, String email, String authNum)
    {
        AuthNum registerAuthNum = authNumRepository.findById(email).orElseThrow(()->{throw new IllegalArgumentException();});
        if(!registerAuthNum.getAuthNum().equals(authNum))
        {
            throw new IllegalArgumentException();
        }

        Optional<User> optUser = userRepository.findByUsernameAndEmail(username, email);
        if(optUser.isEmpty())
        {
            throw new IllegalArgumentException();
        }
        optUser.get().setState(State.ACTIVE);

        authNumRepository.deleteById(email);
    }
    @Transactional
    public void resendAuthNum(AuthNum authNum)
    {
        //mailService.sendAuthMail(registerAuthNum.getEmail(),registerAuthNum.getAuthNum());
        authNumRepository.save(authNum);
    }

    /**
     device add service
     */
    @Transactional(readOnly = true)
    public User getUser(String username)
    {
        return userRepository.findByUsernameAndState(username, State.ACTIVE).orElseThrow(()->{throw new IllegalArgumentException();});
    }
    @Transactional
    public void addMAC(String username, String MAC)
    {
        User user = userRepository.findByUsername(username).orElseThrow(IllegalArgumentException::new);
        UserDevice userDevice = userDeviceRepository.findByMac(MAC).orElseThrow(IllegalArgumentException::new);
        userDevice.setUser(user);
    }

    /**
     * logout
     */
    @Transactional
    public void logout(String username)
    {
        refreshTokenRepository.deleteByUsername(username);
    }

    /**
     * CustomHandshakeHandler
     */
    @Transactional
    public void test_makeAdmin(String username)
    {
        User user = userRepository.findByUsername(username).orElse(null);
        if(user != null)
        {
            //user.setRole(Role.ROLE_ADMIN.toString());
        }
    }
}
