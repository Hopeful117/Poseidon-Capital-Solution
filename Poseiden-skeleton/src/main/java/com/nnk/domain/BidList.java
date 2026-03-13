package com.nnk.springboot.domain;



import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "bidlist")
@RequiredArgsConstructor
@Getter
@Setter
public class BidList {
    // TODO: Map columns in data table BIDLIST with corresponding java fields

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name="BidListId")
    Integer BidListId;

    @Column(name="account")
    @NotNull(message = "Account is mandatory")
    @NotBlank(message = "Account must not be blank")
    String account;

    @Column(name="type")
    @NotNull(message = "Type is mandatory")
    @NotBlank(message = "Type must not be blank")
    String type;

    @Column(name="bidQuantity")
    @Digits(integer=10, fraction=2, message = "Bid Quantity must be a valid number with up to 10 digits and 2 decimal places")
    Double bidQuantity;

    @Column(name="askQuantity")
    @Digits(integer=10, fraction=2, message = "Ask Quantity must be a valid number with up to 10 digits and 2 decimal places")
    Double askQuantity;

    @Column(name="bid")
    @Digits(integer=10, fraction=2, message = "Bid must be a valid number with up to 10 digits and 2 decimal places")
    Double bid;

    @Column(name="ask")
    @Digits(integer=10, fraction=2, message = "Ask must be a valid number with up to 10 digits and 2 decimal places")
    Double ask;

    @Column(name="benchmark")
    String benchmark;

    @Column(name="bidListDate")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Timestamp bidListDate;

    @Column(name="commentary")
    String commentary;

    @Column(name="security")
    String security;

    @Column(name="status")
    String status;

    @Column(name="trader")
    String trader;

    @Column(name="book")
    String book;

    @Column(name="creationName")
    String creationName;

    @Column(name="creationDate")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Timestamp creationDate;

    @Column(name="revisionName")
    String revisionName;

    @Column(name="revisionDate")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Timestamp revisionDate;

    @Column(name="dealName")
    String dealName;

    @Column(name="dealType")
    String dealType;
    @Column(name="sourceListId")
    String sourceListId;

    @Column(name="side")
    String sid;

    public BidList(String account, String type, Double bidQuantity) {
        this.account = account;
        this.type = type;
        this.bidQuantity = bidQuantity;
    }
}
