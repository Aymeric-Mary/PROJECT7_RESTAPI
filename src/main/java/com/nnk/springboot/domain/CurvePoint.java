package com.nnk.springboot.domain;

import com.nnk.springboot.utils.DateUtils;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.sql.Timestamp;
import java.time.Instant;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "curve_point")
public class CurvePoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "curve_id")
    @Positive
    private Integer curveId;

    @Column(name = "as_of_date")
    @Builder.Default
    private Timestamp asOfDate = Timestamp.from(DateUtils.now());;

    @Column(name = "term")
    @PositiveOrZero
    private Double term;

    @Column(name = "val")
    @PositiveOrZero
    private Double value;

    @Column(name = "creation_date")
    @Builder.Default
    private Timestamp creationDate = Timestamp.from(DateUtils.now());

}
