package com.team2.demo.domain.order.entity;

import com.team2.demo.domain.order.dto.ProductWithAmount;
import com.team2.demo.domain.product.entity.Product;
import com.team2.demo.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.util.Pair;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Entity
@Table(name = "ORDERS")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Order {

    @Id
    @Column(name = "ORDER_UUID")
    @Builder.Default
    private String orderUuid = "order-" + UUID.randomUUID();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_UUID", referencedColumnName = "USER_UUID")
    private User buyer;

    @ManyToMany
    @JoinTable(name = "PRODUCT_ORDER_RELATION",
            joinColumns = @JoinColumn(name = "ORDER_UUID"),
            inverseJoinColumns = @JoinColumn(name = "PRODUCT_UUID"))
    @Builder.Default
    private List<Product> products = new ArrayList<>();

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

        this.buyer= user;
        this.deliveryAddress = deliveryAddress;
        this.zipCode = zipCode;
        this.deliveryStatus = deliveryStatus;
    }

    public void updateOrder(Integer totalAmount, String deliveryAddress, Integer zipCode, DeliveryStatus deliveryStatus) {
        if (totalAmount != null) {
            this.totalAmount = totalAmount;
        }
        if (deliveryAddress != null && !deliveryAddress.isBlank()) {
            this.deliveryAddress = deliveryAddress;
        }
        if (zipCode != null) {
            this.zipCode = zipCode;
        }
        if (deliveryStatus != null) {
            this.deliveryStatus = deliveryStatus;
        }
        this.modifiedDate = LocalDateTime.now();
    }

    public void updateOrder(List<ProductWithAmount> updatedProducts, String address, Integer zipCode, DeliveryStatus deliveryStatus) {
        int totalPrice = calculateTotalPrice(updatedProducts);
        this.totalAmount = totalPrice;
        this.deliveryAddress= address;
        this.zipCode = zipCode;
        this.deliveryStatus = deliveryStatus;
        this.modifiedDate = LocalDateTime.now();
        this.products.clear();
        this.products.addAll(updatedProducts.stream().map(ProductWithAmount::product).toList());
    }

    private static int calculateTotalPrice(List<ProductWithAmount> updatedProducts) {
        int totalPrice = 0;
        for(ProductWithAmount item : updatedProducts){
            totalPrice += item.product().getProductPrice()*item.amount();
        }
        return totalPrice;
    }

    public void addItems(List<Pair<Product, Integer>> productsInOrder) {
        productsInOrder.forEach(pair -> {
            for (int i = 0; i < pair.getSecond(); i++) {
                products.add(pair.getFirst());
            }
        });
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

    public void updateProducts(List<Product> newProducts) {
        this.products.clear();
        this.products.addAll(newProducts);
    }

}