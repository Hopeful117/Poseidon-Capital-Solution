package com.nnk.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Représente un utilisateur de l'application.
 * Cette classe gère les informations d'authentification et d'autorisation.
 */
@Setter
@Getter
@Entity
@Table(name = "users")
@RequiredArgsConstructor
public class User implements DomainEntity<User> {
    /**
     * Identifiant unique de l'utilisateur
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    /**
     * Nom d'utilisateur unique pour la connexion
     */
    @NotBlank(message = "Username is mandatory")
    @Column(name = "username", unique = true)
    @Size(max = 125, message = "Username must not exceed 125 characters")
    private String username;

    /**
     * Mot de passe encrypté (au moins 8 caractères, 1 majuscule, 1 chiffre, 1 symbole)
     */
    @NotBlank(message = "Password is mandatory")
    @Column(name = "password")
    @Size(max = 125, message = "Password must not exceed 125 characters")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z\\d]).{8,}$",
            message = "Le mot de passe doit contenir au moins 8 caractères, une majuscule, un chiffre et un symbole."
    )
    private String password;

    /**
     * Nom complet de l'utilisateur
     */
    @NotBlank(message = "FullName is mandatory")
    @Column(name = "fullname")
    @Size(max = 125, message = "FullName must not exceed 125 characters")
    private String fullname;

    /**
     * Rôle de l'utilisateur (ex : USER, ADMIN) — doit correspondre au format attendu par Spring Security (ROLE_USER, ROLE_ADMIN)
     */
    @NotBlank(message = "Role is mandatory")
    @Column(name = "role")
    @Size(max = 125, message = "Role must not exceed 125 characters")
    private String role;

    /**
     * Constructeur pour créer un nouvel utilisateur.
     *
     * @param username le nom d'utilisateur
     * @param password le mot de passe
     * @param fullname le nom complet
     * @param role     le rôle de l'utilisateur
     */
    public User(String username, String password, String fullname, String role) {
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.role = role;
    }

    /**
     * Met à jour l'utilisateur avec les données d'une autre instance.
     *
     * @param domainEntity l'utilisateur contenant les nouvelles données
     * @return l'utilisateur mis à jour
     */
    @Override
    public User update(User domainEntity) {
        fullname = domainEntity.getFullname();
        password = domainEntity.getPassword();
        username = domainEntity.getUsername();
        role = domainEntity.getRole();
        return this;
    }
}
