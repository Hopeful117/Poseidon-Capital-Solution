package com.nnk.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.sql.Timestamp;


@Entity
@Table(name = "curvepoint")
@Getter
@Setter
@RequiredArgsConstructor
public class CurvePoint {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "Id")
    @Digits(integer = 4, fraction = 0, message = "Id must be a valid integer with up to 4 digits")
    Integer id;

    @Column(name = "CurveId")
    Integer curveId;

    @Column(name = "asOfDate")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Timestamp asOfDate;

    @Column(name = "term")
    @Digits(integer = 10, fraction = 2, message = "Term must be a valid number with up to 10 digits and 2 decimal places")
    BigDecimal term;

    @Column(name = "value")
    @Digits(integer = 10, fraction = 2, message = "Value must be a valid number with up to 10 digits and 2 decimal places")
    BigDecimal value;

    @Column(name = "creationDate")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Timestamp creationDate;

    public CurvePoint(Integer curveId, BigDecimal term, BigDecimal value) {
        this.curveId = curveId;
        this.term = term;
        this.value = value;
    }

}
