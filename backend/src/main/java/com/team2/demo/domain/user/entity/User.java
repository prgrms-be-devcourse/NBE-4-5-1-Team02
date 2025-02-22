package com.team2.demo.domain.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.team2.demo.domain.order.entity.Order;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
    @Builder.Default
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
    @JsonIgnore
    @Builder.Default
    private final List<Order> orders = new ArrayList<>();

    public boolean isMine(Order order) {
        return order.getBuyer().equals(this);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof User user)) return false;
        return Objects.equals(id, user.id) && Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }
}

