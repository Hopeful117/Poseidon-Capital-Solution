package com.nnk.repositories;

import com.nnk.domain.BidList;
import com.nnk.domain.BidList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BidListRepository extends JpaRepository<BidList, Integer> {

}
