package com.shvaiale.irishpub.database.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Data
@ToString(callSuper = true, of = "discountCardNumber")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@PrimaryKeyJoinColumn(name = "id_customer")
public class Customer extends Person {

    @Column(name = "discount_card_number", unique = true)
    private Long discountCardNumber;

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private PersonalInformation personalInformation;

    @Builder.Default
    @OneToMany(mappedBy = "customer")
    private Set<FoodOrder> foodOrders = new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "customer")
    private Set<TableOrder> tableOrders = new HashSet<>();

    public void addFoodOrder(FoodOrder foodOrder) {
        foodOrders.add(foodOrder);
        foodOrder.setCustomer(this);
    }

    public void addTableOrder(TableOrder tableOrder) {
        tableOrders.add(tableOrder);
        tableOrder.setCustomer(this);
    }
}
