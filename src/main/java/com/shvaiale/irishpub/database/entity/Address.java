package com.shvaiale.irishpub.database.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@ToString(of = {"houseNumber", "street"})
@EqualsAndHashCode(exclude = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Address {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_customer", nullable = false)
    private PersonalInformation personalInformation;

    @Column(name = "house_number", nullable = false)
    private int houseNumber;

    @Column(nullable = false)
    private String street;
}
