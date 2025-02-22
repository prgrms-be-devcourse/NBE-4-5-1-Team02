package com.team2.demo.domain.user.controller;

import com.team2.demo.domain.user.dto.UserDto;
import com.team2.demo.domain.user.entity.User;
import com.team2.demo.domain.user.service.UserService;
import com.team2.demo.global.response.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserController
{
    private final UserService userService;

    @PostMapping("/users")
    public RsData<UserDto> addUser(@RequestBody UserDto body){

        try{
            User savedUser = userService.addUser(body);
            UserDto userDto = UserDto.of(savedUser);

            return RsData.success("success", userDto);
        }catch (Exception e){
            return RsData.badRequest("사용자 생성에 실패했습니다.", 400);
        }

    }
}
