package com.nnk.services;

import com.nnk.domain.Trade;
import com.nnk.repositories.TradeRepository;
import org.springframework.stereotype.Service;

/**
 * Service CRUD pour les entites Trade.
 */
@Service
public class TradeServiceImpl extends AbstractCrudService<Trade> {

    /**
     * Construit le service a partir du repository Trade.
     *
     * @param repository repository JPA Trade
     */
    protected TradeServiceImpl(TradeRepository repository) {
        super(repository);
    }


}
