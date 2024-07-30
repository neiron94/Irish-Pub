package com.shvaiale.irishpub.database.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Data
@ToString(of = {"name", "price"})
@EqualsAndHashCode(of = "name")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Dish {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_dish")
    private Integer idDish;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private BigDecimal price;

    @Builder.Default
    @ManyToMany(mappedBy = "dishes")
    private Set<FoodOrder> foodOrders = new HashSet<>();
}
