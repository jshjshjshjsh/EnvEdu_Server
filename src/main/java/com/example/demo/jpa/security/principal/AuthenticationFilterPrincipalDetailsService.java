package com.example.demo.jpa.security.principal;

import com.example.demo.jpa.user.model.entity.User;
import com.example.demo.jpa.user.model.enumerate.State;
import com.example.demo.jpa.user.repository.UserRepository;
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

        return optUser.map(AuthenticationFilterPrincipalDetails::new).orElseThrow(()-> new UsernameNotFoundException("username not found"));
    }
}
