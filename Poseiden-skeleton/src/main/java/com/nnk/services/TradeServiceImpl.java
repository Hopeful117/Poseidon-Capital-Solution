package com.nnk.services;

import com.nnk.domain.Trade;
import com.nnk.exceptions.EntityNotFoundException;
import com.nnk.repositories.TradeRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class TradeServiceImpl extends AbstractCrudService<Trade>  {

    protected TradeServiceImpl(TradeRepository repository){
        super(repository);
    }


    

}
