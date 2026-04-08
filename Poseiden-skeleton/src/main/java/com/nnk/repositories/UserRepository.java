package com.nnk.repositories;

import com.nnk.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository JPA pour l'entite User.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    /**
     * Recherche un utilisateur par son nom d'utilisateur.
     *
     * @param username nom d'utilisateur
     * @return utilisateur trouve, si present
     */
    Optional<User> findByUsername(String username);

        /**
        * Vérifie si un utilisateur existe avec le nom d'utilisateur donné.
        *
        * @param username nom d'utilisateur à vérifier
        * @return true si un utilisateur existe avec ce nom d'utilisateur, sinon false
        */
    Boolean existsByUsername(String username);

}
