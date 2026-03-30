package com.nnk.services;

import com.nnk.domain.BidList;
import com.nnk.repositories.BidListRepository;
import org.springframework.stereotype.Service;

@Service
public class BidListServiceImpl extends AbstractCrudService<BidList> {

    protected BidListServiceImpl(BidListRepository repository) {
        super(repository);
    }
}
