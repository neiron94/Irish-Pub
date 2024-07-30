package com.shvaiale.irishpub.database.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Data
@ToString(callSuper = true, of = {"workingPhoneNumber", "email", "salary"})
@EqualsAndHashCode(callSuper = false, of = "email")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@PrimaryKeyJoinColumn(name = "id_worker")
public class Worker extends Person {

    @Column(name = "working_phone_number", nullable = false, unique = true)
    private String workingPhoneNumber;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private int salary;

    @Builder.Default
    @ManyToMany
    @JoinTable(
            name = "can_replace",
            joinColumns = @JoinColumn(name = "replacer"),
            inverseJoinColumns = @JoinColumn(name = "replaceable"))
    Set<Worker> replacers = new HashSet<>();

    @Builder.Default
    @ManyToMany(mappedBy = "replacers")
    Set<Worker> replaceables = new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "worker")
    private Set<TableOrder> tableOrders = new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "worker")
    private Set<FoodOrder> foodOrders = new HashSet<>();

    public void addFoodOrder(FoodOrder foodOrder) {
        foodOrders.add(foodOrder);
        foodOrder.setWorker(this);
    }

    public void addTableOrder(TableOrder tableOrder) {
        tableOrders.add(tableOrder);
        tableOrder.setWorker(this);
    }
}
