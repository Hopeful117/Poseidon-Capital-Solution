package com.nnk.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "rating")
@Getter
@Setter
@RequiredArgsConstructor
public class Rating implements DomainEntity<Rating>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    @Digits(integer = 4, fraction = 0, message = "Id must be a valid integer with up to 4 digits")
    Integer id;

    @Column(name = " moodysRating")
    @Size(max = 125, message = "Moody's Rating must not exceed 125 characters")
    String moodysRating;

    @Column(name = " sandPRating")
    @Size(max = 125, message = "S&P Rating must not exceed 125 characters")
    String sandPRating;

    @Column(name = "fitchRating")
    @Size(max = 125, message = "Fitch Rating must not exceed 125 characters")
    String fitchRating;

    @Column(name = "orderNumber")
    @Positive(message = "Order Number must be positive")
    Integer orderNumber;

    public Rating(String moodysRating, String sandPRating, String fitchRating, Integer orderNumber) {
        this.moodysRating = moodysRating;
        this.sandPRating = sandPRating;
        this.fitchRating = fitchRating;
        this.orderNumber = orderNumber;
    }

    @Override
    public Rating update(Rating rating){
        return this;
    }
}
