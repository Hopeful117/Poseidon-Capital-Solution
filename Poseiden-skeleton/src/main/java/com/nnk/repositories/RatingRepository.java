package com.nnk.repositories;

import com.nnk.domain.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Integer> {

    boolean existsById(Integer id);

    boolean existsByOrderNumber(Integer orderNumber);


}
