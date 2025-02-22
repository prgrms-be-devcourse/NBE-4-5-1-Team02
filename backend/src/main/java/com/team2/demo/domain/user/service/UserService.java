package com.team2.demo.domain.user.service;

import com.team2.demo.domain.user.dto.UserDto;
import com.team2.demo.domain.user.entity.User;
import com.team2.demo.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public User addUser(UserDto userDto){
        System.out.println("유저생성");

        return userRepository.save(
                User.builder()
                .email(userDto.getBuyer().getEmail())
                .createdDate(LocalDateTime.now())
                .modifiedDate(LocalDateTime.now())
                .build());
    }

    @Transactional
    public User findByEmail(String reqEmail) {
        System.out.println("오브젝트체크"+reqEmail);
        return userRepository.findByEmail(reqEmail);
    }
}
