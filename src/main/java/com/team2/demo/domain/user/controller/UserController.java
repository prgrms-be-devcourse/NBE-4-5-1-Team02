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
    public RsData<UserDto> payment(@RequestBody Map<String, Object> body){

        Map<String, Object> buyer = (Map<String, Object>) body.get("buyer");
        User userBody = User.builder()
                .email(buyer.get("email").toString())
                .createdDate(LocalDateTime.now())
                .modifiedDate(LocalDateTime.now())
                .build();
        try{
            User user = userService.addUser(userBody);
            UserDto userDto = UserDto.of(user);
            return RsData.success("success",userDto);
        }catch (Exception e){
            return RsData.badRequest("유저 등록 실패:" +e.getMessage());
        }
    }
}
