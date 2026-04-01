package com.nnk.services;

import com.nnk.domain.CurvePoint;
import com.nnk.repositories.CurvePointRepository;
import org.springframework.stereotype.Service;

/**
 * Service CRUD pour les entites CurvePoint.
 */
@Service
public class CurvePointServiceImpl extends AbstractCrudService<CurvePoint> {

    /**
     * Construit le service a partir du repository CurvePoint.
     *
     * @param repository repository JPA CurvePoint
     */
    protected CurvePointServiceImpl(CurvePointRepository repository) {
        super(repository);
    }

}
