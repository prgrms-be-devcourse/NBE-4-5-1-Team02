package com.team2.demo.web.domain.order.entity;

import com.team2.demo.web.domain.user.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.util.List;

@Entity
public class Order {

    @Id
    private String id;


    @ManyToOne
    private User buyer;
}
