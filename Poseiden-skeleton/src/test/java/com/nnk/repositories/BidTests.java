package com.nnk.repositories;

import com.nnk.springboot.domain.BidList;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BidTests {

    @Autowired
    BidListRepository bidListRepository;


    @Test
    public void bidListTest() {
        BidList bid = new BidList("Account Test", "Type Test", new BigDecimal(10));

        // Save
        bid = bidListRepository.save(bid);
        assertNotNull(bid.getBidListId());
        assertEquals(new BigDecimal(10), bid.getBidQuantity());

        // Update
        bid.setBidQuantity(new BigDecimal(20));
        bid = bidListRepository.save(bid);
        assertEquals(new BigDecimal(20), bid.getBidQuantity());

        // Find
        List<BidList> listResult = bidListRepository.findAll();
        assertFalse(listResult.isEmpty());

        // Delete
        Integer id = bid.getBidListId();
        bidListRepository.delete(bid);
        Optional<BidList> bidList = bidListRepository.findById(id);
        assertFalse(bidList.isPresent());
    }
}
