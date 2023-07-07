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

    @Column(name = "sell_quantity")
    private Double sellQuantity;

    @Column(name = "buy_price")
    private Double buyPrice;

    @Column(name = "sell_price")
    private Double sellPrice;

    @Column(name = "benchmark", length = 125)
    private String benchmark;

    @Column(name = "trade_date")
    private Timestamp tradeDate;

    @Column(name = "security", length = 125)
    private String security;

    @Column(name = "status", length = 10)
    private String status;

    @Column(name = "trader", length = 125)
    private String trader;

    @Column(name = "book", length = 125)
    private String book;

    @Column(name = "creation_name", length = 125)
    private String creationName;

    @Column(name = "creation_date")
    private Timestamp creationDate;

    @Column(name = "revision_name", length = 125)
    private String revisionName;

    @Column(name = "revision_date")
    private Timestamp revisionDate;

    @Column(name = "deal_name", length = 125)
    private String dealName;

    @Column(name = "deal_type", length = 125)
    private String dealType;

    @Column(name = "source_list_id", length = 125)
    private String sourceListId;

    @Column(name = "side", length = 125)
    private String side;
}
