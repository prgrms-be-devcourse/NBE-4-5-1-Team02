package com.team2.demo.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class Buyer {
    @NotNull(message = "이메일은 필수 값 입니다.")
    @Email(message = "올바른 이메일 형식을 작성해주세요.")
    private String email;

    public Buyer(String email) {
        this.email = email;
    }
}
