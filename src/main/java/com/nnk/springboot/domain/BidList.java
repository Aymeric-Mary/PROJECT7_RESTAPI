package com.nnk.springboot.domain;

import com.nnk.springboot.utils.DateUtils;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "bid_list")
public class BidList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotBlank(message = "Account is mandatory")
    @Column(name = "account", nullable = false, length = 30)
    private String account;

    @NotBlank(message = "Type is mandatory")
    @Column(name = "type", nullable = false, length = 30)
    private String type;

    @Column(name = "bid_quantity")
    private Double bidQuantity;

    @Column(name = "ask_quantity")
    private Double askQuantity;

    @Column(name = "bid")
    private Double bid;

    @Column(name = "ask")
    private Double ask;

    @Column(name = "benchmark", length = 125)
    private String benchmark;

    @Column(name = "bid_list_date")
    private Timestamp bidListDate;

    @Column(name = "commentary", length = 125)
    private String commentary;

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
    private Instant creationDate = DateUtils.now();

    @Column(name = "revision_name", length = 125)
    private String revisionName;

    @Column(name = "revision_date")
    private Instant revisionDate;

    @Column(name = "deal_name", length = 125)
    private String dealName;

    @Column(name = "deal_type", length = 125)
    private String dealType;

    @Column(name = "source_list_id", length = 125)
    private String sourceListId;

    @Column(name = "side", length = 125)
    private String side;
}
