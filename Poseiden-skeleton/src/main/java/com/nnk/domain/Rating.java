package com.nnk.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Représente une notation de risque pour les obligations.
 * Gère les différentes agences de notation (Moody's, S&P, Fitch) et le numéro d'ordre.
 */
@Entity
@Table(name = "rating")
@Getter
@Setter
@RequiredArgsConstructor
public class Rating implements DomainEntity<Rating> {

    /** Identifiant unique de la notation */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    @Digits(integer = 4, fraction = 0, message = "Id must be a valid integer with up to 4 digits")
    Integer id;

    /** Notation Moody's */
    @Column(name = " moodysRating")
    @NotBlank
    @Size(max = 125, message = "Moody's Rating must not exceed 125 characters")
    String moodysRating;

    /** Notation Standard & Poor's */
    @Column(name = " sandPRating")
    @NotBlank
    @Size(max = 125, message = "S&P Rating must not exceed 125 characters")
    String sandPRating;

    /** Notation Fitch */
    @Column(name = "fitchRating")
    @NotBlank
    @Size(max = 125, message = "Fitch Rating must not exceed 125 characters")
    String fitchRating;

    /** Numéro d'ordre de la notation */
    @Column(name = "orderNumber")
    @NotNull
    @Positive(message = "Order Number must be positive")
    Integer orderNumber;

    /**
     * Constructeur pour créer une nouvelle notation.
     *
     * @param moodysRating la notation Moody's
     * @param sandPRating la notation S&P
     * @param fitchRating la notation Fitch
     * @param orderNumber le numéro d'ordre
     */
    public Rating(String moodysRating, String sandPRating, String fitchRating, Integer orderNumber) {
        this.moodysRating = moodysRating;
        this.sandPRating = sandPRating;
        this.fitchRating = fitchRating;
        this.orderNumber = orderNumber;
    }

    /**
     * Met à jour la notation avec les données d'une autre instance.
     *
     * @param domainEntity la notation contenant les nouvelles données
     * @return la notation mise à jour
     */
    @Override
    public Rating update(Rating domainEntity) {
        moodysRating = domainEntity.getMoodysRating();
        sandPRating = domainEntity.getSandPRating();
        fitchRating = domainEntity.getFitchRating();
        orderNumber = domainEntity.getOrderNumber();

        return this;
    }
}
