package com.nnk.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Représente une transaction commerciale (Trade).
 * Gère les informations d'achat, de vente et les métadonnées associées aux transactions.
 */
@Entity
@Table(name = "trade")
@Getter
@Setter
@RequiredArgsConstructor
public class Trade implements DomainEntity<Trade> {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "trade_id")
    Integer tradeId;

    @Column(name = "account")
    @NotBlank(message = "Account must not be blank")
    @Size(max = 30, message = "Account must not exceed 30 characters")
    String account;

    @Column(name = "type")
    @NotBlank(message = "Type must not be blank")
    @Size(max = 30, message = "Type must not exceed 30 characters")
    String type;

    @Column(name = "buyQuantity")
    @Positive
    @NotNull
    @Digits(integer = 10, fraction = 2, message = "Buy Quantity must be a valid number with up to 10 digits and 2 decimal places")
    BigDecimal buyQuantity;

    @Column(name = "sellQuantity")
    @Positive
    @Digits(integer = 10, fraction = 2, message = "Sell Quantity must be a valid number with up to 10 digits and 2 decimal places")
    BigDecimal sellQuantity;

    @Column(name = "buyPrice")
    @Positive
    @Digits(integer = 10, fraction = 2, message = "Buy Price must be a valid number with up to 10 digits and 2 decimal places")
    BigDecimal buyPrice;

    @Column(name = "sellPrice")
    @Positive
    @Digits(integer = 10, fraction = 2, message = "Sell Price must be a valid number with up to 10 digits and 2 decimal places")
    BigDecimal sellPrice;

    @Column(name = "benchmark")
    @Size(max = 125, message = "Benchmark must not exceed 125 characters")
    String benchmark;

    @Column(name = "tradeDate")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Timestamp tradeDate;

    @Column(name = "security")
    @Size(max = 125, message = "Security must not exceed 125 characters")
    String security;

    @Column(name = "status")
    @Size(max = 10, message = "Status must not exceed 10 characters")
    String status;

    @Column(name = "trader")
    @Size(max = 125, message = "Trader must not exceed 125 characters")
    String trader;

    @Column(name = "book")
    @Size(max = 125, message = "Book must not exceed 125 characters")
    String book;

    @Column(name = "creationName")
    @Size(max = 125, message = "Creation Name must not exceed 125 characters")
    String creationName;

    @Column(name = "creationDate")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Timestamp creationDate;

    @Column(name = "revisionName")
    @Size(max = 125, message = "Revision Name must not exceed 125 characters")
    String revisionName;

    @Column(name = "revisionDate")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Timestamp revisionDate;

    @Column(name = "dealName")
    @Size(max = 125, message = "Deal Name must not exceed 125 characters")
    String dealName;

    @Column(name = "dealType")
    @Size(max = 125, message = "Deal Type must not exceed 125 characters")
    String dealType;

    @Column(name = "sourceListId")
    @Size(max = 125, message = "Source List Id must not exceed 125 characters")
    String sourceListId;

    @Column(name = "side")
    @Size(max = 125, message = "Side must not exceed 125 characters")
    String side;

    /**
     * Construit un trade minimal.
     *
     * @param account compte associe
     * @param type type de transaction
     * @param buyQuantity quantite achetee
     */
    public Trade(String account, String type, BigDecimal buyQuantity) {
        this.account = account;
        this.type = type;
        this.buyQuantity = buyQuantity;
    }


    /**
     * Retourne l'identifiant technique du trade.
     *
     * @return identifiant du trade
     */
    @Override
    public Integer getId() {
        return tradeId;
    }

    /**
     * Met a jour les champs metier principaux du trade.
     *
     * @param domainEntity trade source
     * @return instance courante mise a jour
     */
    @Override
    public Trade update(Trade domainEntity) {
        account = domainEntity.getAccount();
        type = domainEntity.getType();
        buyQuantity = domainEntity.buyQuantity;


        return this;
    }

}
