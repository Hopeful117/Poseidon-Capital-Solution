package com.nnk.repositories;

import com.nnk.domain.RuleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository JPA pour l'entite RuleName.
 */
@Repository
public interface RuleNameRepository extends JpaRepository<RuleName, Integer> {
}
