package com.shvaiale.irishpub.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Data
@ToString(of = {"email", "phoneNumber", "street", "houseNumber"})
@EqualsAndHashCode(of = "email")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "personal_information")
public class PersonalInformation {

    @Id
    @Column(name = "id_customer")
    private Integer idCustomer;

    @Column(name = "phone_number", nullable = false, unique = true)
    private String phoneNumber;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String street;

    @Column(name = "house_number", nullable = false)
    private Integer houseNumber;
}
