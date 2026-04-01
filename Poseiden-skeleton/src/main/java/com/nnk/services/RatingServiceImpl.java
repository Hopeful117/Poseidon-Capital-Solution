package com.nnk.services;

import com.nnk.domain.Rating;
import com.nnk.repositories.RatingRepository;
import org.springframework.stereotype.Service;

/**
 * Service CRUD pour les entites Rating.
 */
@Service
public class RatingServiceImpl extends AbstractCrudService<Rating> {

    /**
     * Construit le service a partir du repository Rating.
     *
     * @param repository repository JPA Rating
     */
    protected RatingServiceImpl(RatingRepository repository) {
        super(repository);
    }

}
