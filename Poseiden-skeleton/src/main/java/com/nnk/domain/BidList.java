package com.nnk.domain;


import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "bidlist")
@RequiredArgsConstructor
@Getter
@Setter
public class BidList implements DomainEntity<BidList> {
    // TODO: Map columns in data table BIDLIST with corresponding java fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BidListId")
    @Digits(integer = 4, fraction = 0, message = "Id must be a valid integer with up to 4 digits")
    Integer bidListId;

    @Column(name = "account")
    @NotNull(message = "Account is mandatory")
    @NotBlank(message = "Account must not be blank")
    @Size(max = 30, message = "Account must not exceed 30 characters")
    String account;

    @Column(name = "type")
    @NotNull(message = "Type is mandatory")
    @NotBlank(message = "Type must not be blank")
    @Size(max = 30, message = "Type must not exceed 30 characters")
    String type;

    @Column(name = "bidQuantity")
    @Digits(integer = 10, fraction = 2, message = "Bid Quantity must be a valid number with up to 10 digits and 2 decimal places")
    BigDecimal bidQuantity;

    @Column(name = "askQuantity")
    @Digits(integer = 10, fraction = 2, message = "Ask Quantity must be a valid number with up to 10 digits and 2 decimal places")
    BigDecimal askQuantity;

    @Column(name = "bid")
    @Digits(integer = 10, fraction = 2, message = "Bid must be a valid number with up to 10 digits and 2 decimal places")
    BigDecimal bid;

    @Column(name = "ask")
    @Digits(integer = 10, fraction = 2, message = "Ask must be a valid number with up to 10 digits and 2 decimal places")
    BigDecimal ask;

    @Column(name = "benchmark")
    @Size(max = 125, message = "Benchmark must not exceed 125 characters")
    String benchmark;

    @Column(name = "bidListDate")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Timestamp bidListDate;

    @Column(name = "commentary")
    @Size(max = 125, message = "Commentary must not exceed 125 characters")

    String commentary;

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
    String sid;

    public BidList(String account, String type, BigDecimal bidQuantity) {
        this.account = account;
        this.type = type;
        this.bidQuantity = bidQuantity;
    }

    public Integer getId(){
        return bidListId;
    }

    public BidList update( BidList domainEntity){

        account=domainEntity.getAccount();
        type=domainEntity.getType();
        bidQuantity=domainEntity.getBidQuantity();

        return this;

    }
}
