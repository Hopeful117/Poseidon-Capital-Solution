package com.nnk.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.tomcat.util.digester.Rule;

import java.sql.Timestamp;

@Entity
@Table(name = "rulename")
@Getter
@Setter
@RequiredArgsConstructor
public class RuleName {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    Integer id;

    @Column(name = "name", length = 125)
    String name;

    @Column(name = "description", length = 125)
    String description;

    @Column(name = "json",length = 125)
    String json;

    @Column(name = "template",length = 512)
    String template;

    @Column(name = "sql_str",length = 125)
    String sqlStr;

    @Column(name = "sql_part",length = 125)
    String sqlPart;

    public RuleName(String name , String description, String json, String template, String sqlStr, String sqlPart) {
        this.name = name;
        this.description = description;
        this.json = json;
        this.template = template;
        this.sqlStr = sqlStr;
        this.sqlPart = sqlPart;
    }


}
