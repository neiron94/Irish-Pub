package com.shvaiale.irishpub.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Data
@ToString(of = {"email", "phoneNumber"})
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

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "id_customer", nullable = false)
    private Customer customer;

    @Builder.Default
    @OneToMany(mappedBy = "personalInformation")
    private Set<Address> addresses = new HashSet<>();

    public void addAddress(Address address) {
        addresses.add(address);
        address.setPersonalInformation(this);
    }
}
