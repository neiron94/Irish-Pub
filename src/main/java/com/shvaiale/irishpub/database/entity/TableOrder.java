package com.shvaiale.irishpub.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@ToString(of = {"customer", "time", "tableNumber"})
@EqualsAndHashCode(of = {"customer", "time"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "table_order")
public class TableOrder {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_customer", nullable = false)
    private Customer customer;

    @Column(nullable = false)
    private LocalDateTime time;

    @Column(name = "table_number", nullable = false)
    private short tableNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_worker", nullable = false)
    private Worker worker;
}
