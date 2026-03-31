package com.nnk.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.sql.Timestamp;


@Entity
@Table(name = "trade")
@Getter
@Setter
@RequiredArgsConstructor
public class Trade implements DomainEntity<Trade> {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "trade_id")
    @Digits(integer = 4, fraction = 0, message = "Id must be a valid integer with up to 4 digits")
    Integer tradeId;

    @Column(name = "account")
    @NotNull(message = "Account must not be null")
    @NotBlank(message = "Account must not be blank")
    @Size(max = 30, message = "Account must not exceed 30 characters")
    String account;

    @Column(name = "type")
    @NotNull(message = "Type must not be null")
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

    public Trade(String account, String type, BigDecimal buyQuantity) {
        this.account = account;
        this.type = type;
        this.buyQuantity = buyQuantity;
    }


    public Integer getId() {
        return tradeId;
    }

    @Override
    public Trade update(Trade domainEntity) {
        account = domainEntity.getAccount();
        type = domainEntity.getType();
        buyQuantity = domainEntity.buyQuantity;


        return this;
    }


}
