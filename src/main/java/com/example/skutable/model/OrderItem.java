package com.example.skutable.model;


import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity(name = "t_order_item")
@Data
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "drug", nullable = false)
    private String drug;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "department", nullable = false)
    private String department;

    @Column(name = "sufferer", nullable = false)
    private String sufferer;
}
