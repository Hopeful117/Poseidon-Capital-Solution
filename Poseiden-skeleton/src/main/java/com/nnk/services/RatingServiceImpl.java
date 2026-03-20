package com.nnk.services;

import com.nnk.domain.Rating;
import com.nnk.repositories.RatingRepository;
import org.springframework.stereotype.Service;

@Service
public class RatingServiceImpl extends AbstractCrudService<Rating>{

    protected RatingServiceImpl(RatingRepository repository) {
        super(repository);
    }

}
