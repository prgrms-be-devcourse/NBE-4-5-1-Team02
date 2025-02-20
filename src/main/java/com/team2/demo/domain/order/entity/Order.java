package com.team2.demo.domain.order.entity;

import com.team2.demo.domain.product.entity.Product;
import com.team2.demo.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "ORDERS")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Order {

    @Id
    @Column(name = "ORDER_UUID")
    private String orderUuid;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToMany
    @JoinTable(name = "PRODUCT_ORDER_RELATION",
            joinColumns = @JoinColumn(name = "ORDER_UUID"),
            inverseJoinColumns = @JoinColumn(name = "PRODUCT_UUID"))
    private final List<Product> products = new ArrayList<>();

    @CreatedDate
    @Column(name = "CREATE_DATE")
    private LocalDateTime createDate;

    @LastModifiedDate
    @Column(name = "MODIFIED_DATE")
    private LocalDateTime modifiedDate;

    @Column(name = "TOTAL_AMOUNT")
    private Integer totalAmount;

    @Column(name = "DELIVERY_ADDRESS")
    private String deliveryAddress;

    @Column(name = "ZIP_CODE")
    private Integer zipCode;



    @Enumerated(EnumType.STRING)
    @Column(name = "DELIVERY_STATUS")
    private DeliveryStatus deliveryStatus;



    public Order(User user, String deliveryAddress, Integer zipCode, DeliveryStatus  deliveryStatus) {

        this.user = user;
        this.deliveryAddress = deliveryAddress;
        this.zipCode = zipCode;
        this.deliveryStatus = deliveryStatus;
    }

    public enum DeliveryStatus {

        PENDING, SHIPPED, DELIVERED, CANCELLED
    }

    public void updateDeliveryInfo(String deliveryAddress, Integer zipCode) {
        this.deliveryAddress = deliveryAddress;
        this.zipCode = zipCode;
    }

    public void updateDeliveryStatus(DeliveryStatus deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public void updateModifiedDate() {
        this.modifiedDate = LocalDateTime.now();

    }

}