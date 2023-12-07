package com.nnk.springboot.domain;

import com.nnk.springboot.utils.DateUtils;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
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
    @PositiveOrZero
    private Double bidQuantity;

    @Column(name = "creation_date")
    @Builder.Default
    private Instant creationDate = DateUtils.now();

    @Column(name = "revision_date")
    @Builder.Default
    private Instant revisionDate = DateUtils.now();
}
