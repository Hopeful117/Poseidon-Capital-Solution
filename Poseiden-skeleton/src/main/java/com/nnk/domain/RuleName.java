package com.nnk.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "rulename")
@Getter
@Setter
@RequiredArgsConstructor
public class RuleName implements DomainEntity<RuleName>{
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    @Digits(integer = 4, fraction = 0, message = "Id must be a valid integer with up to 4 digits")
    Integer id;

    @Column(name = "name")
    @Size(max = 125, message = "Name must not exceed 125 characters")
    String name;

    @Column(name = "description")
    @Size(max = 125, message = "Description must not exceed 125 characters")
    String description;

    @Column(name = "json")
    @Size(max = 125, message = "Json must not exceed 125 characters")
    String json;

    @Column(name = "template")
    @Size(max = 512, message = "Template must not exceed 512 characters")
    String template;

    @Column(name = "sql_str")
    @Size(max = 125, message = "SqlStr must not exceed 125 characters")
    String sqlStr;

    @Column(name = "sql_part")
    @Size(max = 125, message = "SqlPart must not exceed 125 characters")
    String sqlPart;

    public RuleName(String name, String description, String json, String template, String sqlStr, String sqlPart) {
        this.name = name;
        this.description = description;
        this.json = json;
        this.template = template;
        this.sqlStr = sqlStr;
        this.sqlPart = sqlPart;
    }


    @Override
    public RuleName update(RuleName domainEntity) {
        return this;
    }
}
