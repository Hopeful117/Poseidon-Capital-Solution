package com.nnk.repositories;

import com.nnk.domain.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository JPA pour l'entite Rating.
 */
@Repository
public interface RatingRepository extends JpaRepository<Rating, Integer> {


}
