package com.team2.demo.web.domain.user.entity;

import com.team2.demo.web.domain.order.entity.Order;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "MEMBER")
public class User {

    @Id
    private final String id = "user-" + UUID.randomUUID();

    @Email
    private String email;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

    @OneToMany(mappedBy = "buyer")
    private final List<Order> orders = new ArrayList<>();

    @Builder
    public User(String email) {
        this.email = email;
    }
}
