package com.example.demo.user.model.enumerate;

import com.example.demo.user.dto.request.RegisterDTO;
import com.example.demo.user.model.entity.Educator;
import com.example.demo.user.model.entity.Student;
import com.example.demo.user.model.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;

public enum Role {
    ROLE_STUDENT {
        @Override
        public User generateUserByRole(RegisterDTO registerDTO, PasswordEncoder passwordEncoder) {
            return Student.studentBuilder()
                    .username(registerDTO.getUsername())
                    .password(passwordEncoder.encode(registerDTO.getPassword()))
                    .email(registerDTO.getEmail())
                    .gender(registerDTO.getGender())
                    .role(registerDTO.getRole())
                    .state(State.ACTIVE)
                    .birthday(registerDTO.getBirthday())
                    .build();
        }
    }, ROLE_EDUCATOR {
        @Override
        public User generateUserByRole(RegisterDTO registerDTO, PasswordEncoder passwordEncoder) {
            return Educator.educatorBuilder()
                    .username(registerDTO.getUsername())
                    .password(passwordEncoder.encode(registerDTO.getPassword()))
                    .email(registerDTO.getEmail())
                    .gender(registerDTO.getGender())
                    .role(registerDTO.getRole())
                    .state(State.ACTIVE)
                    .isAuthorized(IsAuthorized.NO)
                    .birthday(registerDTO.getBirthday())
                    .build();
        }
    };

    public abstract User generateUserByRole(RegisterDTO registerDTO, PasswordEncoder passwordEncoder);
}
