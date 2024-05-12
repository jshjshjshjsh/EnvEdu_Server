package com.example.demo.security.principal;

import com.example.demo.user.model.entity.User;
import com.example.demo.user.model.enumerate.State;
import com.example.demo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationFilterPrincipalDetailsService implements PrincipalDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info("들어온 유저이름 : " + username);
        Optional<User> checkUser = userRepository.findByUsername(username);
        Optional<User> optUser = userRepository.findByUsernameAndState(username, State.ACTIVE);
        log.info("optUser? : " + optUser);
        log.info("checkUser? : " + checkUser);

        log.info("유저 이름으로 못찾음 : " + optUser.map(User::getUsername).orElse("null상태임"));
        return optUser.map(AuthenticationFilterPrincipalDetails::new).orElseThrow(()-> new UsernameNotFoundException("username not found"));
    }
}
