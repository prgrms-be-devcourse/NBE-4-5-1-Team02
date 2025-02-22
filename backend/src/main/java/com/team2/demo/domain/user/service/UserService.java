package com.team2.demo.domain.user.service;

import com.team2.demo.domain.user.entity.User;
import com.team2.demo.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public User addUser(User user){
        System.out.println("결제 진행 서비스 시작");
        return userRepository.save(user);
        //return null;
    }

    @Transactional
    public User findByEmail(String reqEmail) {
        System.out.println("오브젝트체크"+reqEmail);
        return userRepository.findByEmail(reqEmail);
    }
}
