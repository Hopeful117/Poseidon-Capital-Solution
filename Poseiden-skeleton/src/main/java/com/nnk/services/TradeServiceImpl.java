package com.nnk.services;

import com.nnk.domain.Trade;
import com.nnk.repositories.TradeRepository;
import org.springframework.stereotype.Service;

@Service
public class TradeServiceImpl extends AbstractCrudService<Trade> {

    protected TradeServiceImpl(TradeRepository repository) {
        super(repository);
    }


}
