package com.shvaiale.irishpub.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@ToString(of = {"customer", "time"})
@EqualsAndHashCode(of = {"customer", "time"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "food_order")
public class FoodOrder {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_customer", nullable = false)
    private Customer customer;

    @Column(nullable = false)
    private LocalDateTime time;

    @Builder.Default
    @ManyToMany
    @JoinTable(
            name = "food_order_contains_dish",
            joinColumns = @JoinColumn(name = "id_food_order"),
            inverseJoinColumns = @JoinColumn(name = "id_dish")
    )
    private Set<Dish> dishes = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(
            name = "worker_takes_food_order",
            joinColumns = @JoinColumn(name = "id_food_order"),
            inverseJoinColumns = @JoinColumn(name = "id_worker")
    )
    private Worker worker;
}
