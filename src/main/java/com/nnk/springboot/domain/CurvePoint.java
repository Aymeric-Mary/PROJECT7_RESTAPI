package com.nnk.springboot.domain;

import com.nnk.springboot.utils.DateUtils;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

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

    @Column(name = "term")
    @PositiveOrZero
    private Double term;

    @Column(name = "val")
    @PositiveOrZero
    private Double value;

    @Column(name = "creation_date", nullable = false)
    @Builder.Default
    private Instant creationDate = DateUtils.now();

    @Column(name = "as_of_date", nullable = false)
    @Builder.Default
    private Instant asOfDate = DateUtils.now();
}
