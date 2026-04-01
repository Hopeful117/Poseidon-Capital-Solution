package com.nnk.repositories;

import com.nnk.domain.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository JPA pour l'entite Rating.
 */
@Repository
public interface RatingRepository extends JpaRepository<Rating, Integer> {

    /**
     * Recherche un rating par numero d'ordre.
     *
     * @param orderNumber numero d'ordre
     * @return rating trouve, si present
     */
    Optional<Rating> findByOrderNumber(Integer orderNumber);

    /**
     * Indique si un rating existe pour l'identifiant donne.
     *
     * @param id identifiant du rating
     * @return true si un rating existe
     */
    boolean existsById(Integer id);

    /**
     * Indique si un rating existe pour le numero d'ordre donne.
     *
     * @param orderNumber numero d'ordre
     * @return true si un rating existe
     */
    boolean existsByOrderNumber(Integer orderNumber);


}
