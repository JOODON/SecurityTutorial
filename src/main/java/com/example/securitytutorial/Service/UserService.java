package com.example.securitytutorial.Service;

import com.example.securitytutorial.Entity.User;
import com.example.securitytutorial.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public void saveUser(User user){
        user.setRole("ROLE_USER");

        String encPassword = setPasswordEncoder(user);
        user.setPassword(encPassword);

        userRepository.save(user);
    }
    private String setPasswordEncoder(User user){
        String rawPassword = user.getPassword();

        return passwordEncoder.encode(rawPassword);
    }

    public User findByUsername(String username){
        return userRepository.findByUserName(username);
    }
    public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }
}
