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
@Table(name = "rating")
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "moodys_rating", length = 125)
    private String moodysRating;

    @Column(name = "sand_p_rating", length = 125)
    private String sandPRating;

    @Column(name = "fitch_rating", length = 125)
    private String fitchRating;

    @Column(name = "order_number")
    @Positive
    private Integer orderNumber;

}
