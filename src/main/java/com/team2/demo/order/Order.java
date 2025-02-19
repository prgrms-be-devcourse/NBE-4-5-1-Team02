package com.team2.demo.order;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Entity
@Table(name = "ORDER")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Order {

    @Id
    @Column(name = "ORDER_UUID")
    private String orderUuid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_UUID")
    private User user;

    @ManyToMany
    @JoinTable(name = "PRODUCT_ORDER_RELATION",
            joinColumns = @JoinColumn(name = "ORDER_UUID"),
            inverseJoinColumns = @JoinColumn(name = "PRODUCT_UUID"))
    private List<Product> products = new ArrayList<>();


    @Column(name = "CREATE_DATE")
    private LocalDateTime createDate;

    @Column(name = "MODIFIED_DATE")
    private LocalDateTime modifiedDate;

    @Column(name = "TOTAL_AMOUNT")
    private Integer totalAmount;

    @Column(name = "DELIVERY_ADDRESS")
    private String deliveryAddress;

    @Column(name = "ZIP_CODE")
    private Integer zipCode;

    @Column(name = "DELIVERY_STATUS")
    private String deliveryStatus;

    public Order(User user, String deliveryAddress, Integer zipCode, String deliveryStatus) {
        this.user = user;
        this.deliveryAddress = deliveryAddress;
        this.zipCode = zipCode;
        this.deliveryStatus = deliveryStatus;
    }

}