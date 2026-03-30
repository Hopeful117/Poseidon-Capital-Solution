package com.nnk.services;

import com.nnk.domain.CurvePoint;
import com.nnk.repositories.CurvePointRepository;
import org.springframework.stereotype.Service;

@Service
public class CurvePointServiceImpl extends AbstractCrudService<CurvePoint> {

    protected CurvePointServiceImpl(CurvePointRepository repository) {
        super(repository);
    }

}
