package com.nnk.springboot.domain;

import jakarta.persistence.*;
import lombok.*;

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

    @Column(name = "moodys_rating",length = 125)
    private String moodysRating;

    @Column(name = "sand_rating",length = 125)
    private String sandRating;

    @Column(name = "fitch_rating",length = 125)
    private String fitchRating;

    @Column(name = "order_number")
    private Integer orderNumber;

}
