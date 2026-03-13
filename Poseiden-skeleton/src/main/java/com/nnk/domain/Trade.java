package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;


@Entity
@Table(name = "trade")
@Getter
@Setter
@RequiredArgsConstructor
public class Trade {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "TradeId", nullable = false)
    Integer tradeId;

    @Column(name = "account")
    @NotNull(message = "Account must not be null")
    @NotBlank(message = "Account must not be blank")
    String account;

    @Column(name = "type")
    @NotNull(message = "Type must not be null")
    @NotBlank(message = "Type must not be blank")
    String type;

    @Column(name = "buyQuantity")
    @Digits(integer = 10, fraction = 2, message = "Buy Quantity must be a valid number with up to 10 digits and 2 decimal places")
    Double buyQuantity;

    @Column(name = "sellQuantity")
    @Digits(integer = 10, fraction = 2, message = "Sell Quantity must be a valid number with up to 10 digits and 2 decimal places")
    Double sellQuantity;

    @Column(name = "buyPrice")
    @Digits(integer = 10, fraction = 2, message = "Buy Price must be a valid number with up to 10 digits and 2 decimal places")
    Double buyPrice;

    @Column(name = "sellPrice")
    @Digits(integer = 10, fraction = 2, message = "Sell Price must be a valid number with up to 10 digits and 2 decimal places")
    Double sellPrice;

    @Column(name = "benchmark")
    String benchmark;

    @Column(name = "tradeDate")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Timestamp tradeDate;

    @Column(name = "security")
    String security;

    @Column(name = "status")
    String status;

    @Column(name = "trader")
    String trader;

    @Column(name = "book")
    String book;

    @Column(name = "creationName")
    String creationName;

    @Column(name = "creationDate")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Timestamp creationDate;

    @Column(name = "revisionName")
    String revisionName;

    @Column(name = "revisionDate")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Timestamp revisionDate;

    @Column(name = "dealName")
    String dealName;

    @Column(name = "dealType")
    String dealType;

    @Column(name = "sourceListId")
    String sourceListId;

    @Column(name = "side")
    String side;

    public Trade(String account,String type) {
        this.account = account;
        this.type = type;
    }
    // TODO: Map columns in data table TRADE with corresponding java fields
}
