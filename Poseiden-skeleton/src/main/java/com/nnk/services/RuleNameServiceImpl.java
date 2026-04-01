package com.nnk.services;

import com.nnk.domain.RuleName;
import com.nnk.repositories.RuleNameRepository;
import org.springframework.stereotype.Service;

/**
 * Service CRUD pour les entites RuleName.
 */
@Service
public class RuleNameServiceImpl extends AbstractCrudService<RuleName> {
    /**
     * Construit le service a partir du repository RuleName.
     *
     * @param repository repository JPA RuleName
     */
    protected RuleNameServiceImpl(RuleNameRepository repository) {
        super(repository);
    }
}
