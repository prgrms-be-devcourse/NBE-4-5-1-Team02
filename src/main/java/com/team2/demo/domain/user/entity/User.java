package com.team2.demo.domain.user.entity;

import com.team2.demo.domain.order.entity.Order;
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
    @Column(name = "USER_UUID")
    private final String id = "user-" + UUID.randomUUID();

    @Email
    @Column(name = "EMAIL")
    private String email;

    @CreatedDate
    @Column(name = "CREATE_DATE")
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(name = "MODIFIED_DATE")
    private LocalDateTime modifiedDate;

    @OneToMany(mappedBy = "buyer")
    private final List<Order> orders = new ArrayList<>();

    @Builder
    public User(String email) {
        this.email = email;
    }
}
