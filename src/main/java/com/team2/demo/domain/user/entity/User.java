package com.team2.demo.domain.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.team2.demo.domain.order.entity.Order;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "MEMBER")
public class User {

    @Id
    @Column(name = "USER_UUID")
<<<<<<< HEAD
=======
    @Builder.Default
>>>>>>> 9afa7b447a054d0dc8a1bf1a71ff070596e106b6
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

    @OneToMany(mappedBy = "user")
<<<<<<< HEAD
    private final List<Order> orders = new ArrayList<>();

    @Builder
    public User(String email) {
        this.email = email;
    }
=======
    @JsonIgnore
    @Builder.Default
    private final List<Order> orders = new ArrayList<>();

>>>>>>> 9afa7b447a054d0dc8a1bf1a71ff070596e106b6
}
