package com.nnk.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;
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
    @NotBlank(message = "Name must not be blank")
    @Size(max = 125, message = "Name must not exceed 125 characters")
    String name;

    @Column(name = "description")
    @NotBlank(message = "Description must not be blank")
    @Size(max = 125, message = "Description must not exceed 125 characters")
    String description;

    @Column(name = "json")
    @NotBlank(message = "Json must not be blank")
    @Size(max = 125, message = "Json must not exceed 125 characters")
    String json;

    @Column(name = "template")
    @NotBlank(message = "Template must not be blank")
    @Size(max = 512, message = "Template must not exceed 512 characters")
    String template;

    @Column(name = "sql_str")
    @NotBlank(message = "SqlStr must not be blank")
    @Size(max = 125, message = "SqlStr must not exceed 125 characters")
    String sqlStr;

    @Column(name = "sql_part")
    @NotBlank(message = "SqlPart must not be blank")
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
        name=domainEntity.getName();
        description=domainEntity.getDescription();
        json=domainEntity.getJson();
        template=domainEntity.getTemplate();
        sqlStr=domainEntity.getSqlStr();
        sqlPart=domainEntity.getSqlPart();
        return this;
    }
}
