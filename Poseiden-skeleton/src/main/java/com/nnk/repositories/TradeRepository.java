package com.nnk.repositories;

import com.nnk.domain.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository JPA pour l'entite Trade.
 */
@Repository
public interface TradeRepository extends JpaRepository<Trade, Integer> {
}
