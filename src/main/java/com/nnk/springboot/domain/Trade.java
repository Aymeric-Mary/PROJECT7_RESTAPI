package com.nnk.springboot.domain;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "trade")
public class Trade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "account", length = 30, nullable = false)
    private String account;

    @Column(name = "type", length = 30, nullable = false)
    private String type;

    @Column(name = "buy_quantity")
    private Double buyQuantity;

    @Column(name = "creation_date")
    private Timestamp creationDate;

    @Column(name = "revision_date")
    private Timestamp revisionDate;
}
