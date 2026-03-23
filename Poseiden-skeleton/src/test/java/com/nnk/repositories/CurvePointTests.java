package com.nnk.repositories;

import com.nnk.domain.CurvePoint;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CurvePointTests {

    @Autowired
    CurvePointRepository curvePointRepository;

    @Test
    void curvePointTest() {

        CurvePoint curvePoint = new CurvePoint( new BigDecimal(10), new BigDecimal(30));

        // Save
        curvePoint = curvePointRepository.save(curvePoint);


        // Update
        curvePoint.setCurveId(20);
        curvePointRepository.save(curvePoint);
        assertEquals(20, curvePointRepository.findById(curvePoint.getId()).get().getCurveId());

        // Find
        Optional<CurvePoint> found = curvePointRepository.findById(curvePoint.getId());
        assertTrue(found.isPresent());

        //Find all
        assertFalse(curvePointRepository.findAll().isEmpty());

        // Delete
        Integer id = curvePoint.getId();
        curvePointRepository.delete(curvePoint);
        assertTrue(curvePointRepository.findById(id).isEmpty());
    }
}