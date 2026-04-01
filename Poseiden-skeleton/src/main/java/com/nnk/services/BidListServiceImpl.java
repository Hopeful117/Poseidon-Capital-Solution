package com.nnk.services;

import com.nnk.domain.BidList;
import com.nnk.repositories.BidListRepository;
import org.springframework.stereotype.Service;

/**
 * Service CRUD pour les entites BidList.
 */
@Service
public class BidListServiceImpl extends AbstractCrudService<BidList> {

    /**
     * Construit le service a partir du repository BidList.
     *
     * @param repository repository JPA BidList
     */
    protected BidListServiceImpl(BidListRepository repository) {
        super(repository);
    }
}
