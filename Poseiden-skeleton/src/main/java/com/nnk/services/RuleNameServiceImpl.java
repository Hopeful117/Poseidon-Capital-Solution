package com.nnk.services;

import com.nnk.domain.RuleName;
import com.nnk.repositories.RuleNameRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class RuleNameServiceImpl extends AbstractCrudService<RuleName> {
    protected RuleNameServiceImpl(RuleNameRepository repository) {
        super(repository);
    }
}
