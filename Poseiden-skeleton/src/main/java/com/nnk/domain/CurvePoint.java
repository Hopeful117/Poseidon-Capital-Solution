package com.nnk.springboot.domain;

import jakarta.validation.constraints.Digits;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;


@Entity
@Table(name = "curvepoint")
@Getter
@Setter
@RequiredArgsConstructor
public class CurvePoint {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name="Id")
    Integer id;

    @Column(name="CurveId")
    Integer curveId;

    @Column(name="asOfDate")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Timestamp asOfDate;

    @Column(name="term")
    @Digits(integer=10, fraction=2, message = "Term must be a valid number with up to 10 digits and 2 decimal places")
    Double term;

    @Column(name="value")
    @Digits(integer=10, fraction=2, message = "Value must be a valid number with up to 10 digits and 2 decimal places")
    Double value;

    @Column(name="creationDate")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Timestamp creationDate;

    public CurvePoint(Integer curveId, double term, double value) {
        this.curveId = curveId;
        this.term = term;
        this.value = value;
    }

}
