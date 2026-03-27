package com.nnk.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@Entity
@Table(name = "users")
@RequiredArgsConstructor
public class User implements DomainEntity <User>{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id")
    @Digits(integer = 4, fraction = 0, message = "Id must be a valid integer with up to 4 digits")
    private Integer id;

    @NotBlank(message = "Username is mandatory")
    @Column(name = "username", unique = true)
    @Size(max = 125, message = "Username must not exceed 125 characters")
    private String username;

    @NotBlank(message = "Password is mandatory")
    @Column(name = "password")
    @Size(max = 125, message = "Password must not exceed 125 characters")
    @Pattern(
            regexp= "^(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z\\d]).{8,}$",
            message = "Le mot de passe doit contenir au moins 8 caractères, une majuscule, un chiffre et un symbole."
    )
    private String password;


    @NotBlank(message = "FullName is mandatory")
    @Column(name = "fullname")
    @Size(max = 125, message = "FullName must not exceed 125 characters")
    private String fullname;

    @NotBlank(message = "Role is mandatory")
    @Column(name = "role")
    @Size(max = 125, message = "Role must not exceed 125 characters")
    private String role;

    public User(String username, String password, String fullname, String role) {
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.role = role;
    }

    @Override
    public User update(User domainEntity) {
        fullname = domainEntity.getFullname();
        password = domainEntity.getPassword();
        username = domainEntity.getUsername();
        role = domainEntity.getRole();
        return this;
    }
}
