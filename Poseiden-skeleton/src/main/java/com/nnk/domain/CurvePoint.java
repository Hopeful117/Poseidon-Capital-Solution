package com.nnk.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Représente un point sur une courbe de taux d'intérêt.
 * Gère les informations des points de courbe avec les termes et les valeurs.
 */
@Entity
@Table(name = "curvepoint")
@Getter
@Setter
@RequiredArgsConstructor
public class CurvePoint implements DomainEntity<CurvePoint> {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "Id")
    Integer id;

    @Column(name = "CurveId")
    @Positive
    @NotNull
    Integer curveId;

    @Column(name = "asOfDate")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Timestamp asOfDate;

    @Column(name = "term")
    @NotNull
    @Positive
    @Digits(integer = 10, fraction = 2, message = "Term must be a valid number with up to 10 digits and 2 decimal places")
    BigDecimal term;

    @Column(name = "curveValue")
    @NotNull
    @Digits(integer = 10, fraction = 2, message = "Value must be a valid number with up to 10 digits and 2 decimal places")
    BigDecimal value;

    @Column(name = "creationDate")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Timestamp creationDate;

    /**
     * Construit un point de courbe minimal.
     *
     * @param curveId identifiant de la courbe
     * @param term maturite du point
     * @param value valeur du point
     */
    public CurvePoint(Integer curveId, BigDecimal term, BigDecimal value) {
        this.curveId = curveId;
        this.term = term;
        this.value = value;
    }

    /**
     * Met a jour les informations principales du point de courbe.
     *
     * @param domainEntity point source
     * @return instance courante mise a jour
     */
    @Override
    public CurvePoint update(CurvePoint domainEntity) {
        curveId = domainEntity.getCurveId();
        term = domainEntity.getTerm();
        value = domainEntity.getValue();

        return this;
    }
}
