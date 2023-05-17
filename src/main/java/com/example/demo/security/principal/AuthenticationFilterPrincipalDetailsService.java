package com.example.demo.security.principal;

import com.example.demo.user.model.entity.User;
import com.example.demo.user.model.enumerate.State;
import com.example.demo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationFilterPrincipalDetailsService implements PrincipalDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optUser = userRepository.findByUsernameAndState(username, State.ACTIVE);
        return optUser.map(AuthenticationFilterPrincipalDetails::new).orElseThrow(()-> new UsernameNotFoundException("username not found"));
    }
}
