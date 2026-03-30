package com.nnk.services;

import com.nnk.domain.CurvePoint;
import com.nnk.exceptions.EntityNotFoundException;
import com.nnk.repositories.CurvePointRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)

public class CurvePointServiceTest {


    @Mock
    private CurvePointRepository curvePointRepository;

    @InjectMocks
    private CurvePointServiceImpl curvePointService;

    @ParameterizedTest
    @CsvSource({"1, 10.0, 5.0",
            "2, 20.0, 4.0",
            "3, 30.0, 3.0"
    })
    public void testAddCurvePoint(int curveId, BigDecimal term, BigDecimal value) {

        CurvePoint curvePoint = new CurvePoint(curveId, term, value);
        // When
        curvePointService.create(curvePoint);
        ArgumentCaptor<CurvePoint> captor = ArgumentCaptor.forClass(CurvePoint.class);

        // Then
        verify(curvePointRepository).save(captor.capture());
        CurvePoint savedCurvePoint = captor.getValue();

        assertEquals(savedCurvePoint.getCurveId(), curveId);
        assertEquals(savedCurvePoint.getTerm(), term);
        assertEquals(savedCurvePoint.getValue(), value);


    }


    @Test
    public void testGetAllCurvePoints() {
        //Given

        List<CurvePoint> mockCurvePoints = List.of(
                new CurvePoint(10, BigDecimal.valueOf(10.0), BigDecimal.valueOf(5.0)),
                new CurvePoint(11, BigDecimal.valueOf(20.0), BigDecimal.valueOf(4.0))
        );
        when(curvePointRepository.findAll()).thenReturn(mockCurvePoints);
        // When
        List<CurvePoint> curvePoints = curvePointService.findAll();


        // Then
        assert (curvePoints.size() == 2);

        assert (curvePoints.get(1).getTerm().equals(BigDecimal.valueOf(20.0)));
        assert (curvePoints.get(0).getValue().equals(BigDecimal.valueOf(5.0)));


    }

    @Test
    public void testGetCurvePointById() {
        // Given
        CurvePoint mockCurvePoint = new CurvePoint(12, BigDecimal.valueOf(10.0), BigDecimal.valueOf(5.0));
        when(curvePointRepository.findById(anyInt())).thenReturn(java.util.Optional.of(mockCurvePoint));

        // When
        CurvePoint curvePoint = curvePointService.findById(1);

        // Then
        assert (curvePoint.getCurveId().equals(12));
        assert (curvePoint.getTerm().equals(BigDecimal.valueOf(10.0)));
        assert (curvePoint.getValue().equals(BigDecimal.valueOf(5.0)));
    }


    @Test
    public void testGetCurvePointByIdShouldThrowExceptionWhenCurvePointNotFound() {
        // Given
        when(curvePointRepository.findById(anyInt())).thenReturn(java.util.Optional.empty());

        // When & Then
        assertThrows(EntityNotFoundException.class, () -> curvePointService.findById(999));

    }

    @ParameterizedTest
    @CsvSource({
            "1, 1, 10.0, 5.0, 20.0, 6.0",
            "2, 2, 20.0, 4.0, 30.0, 5.0",
            "3, 3, 30.0, 3.0, 40.0, 4.0"
    })
    public void testUpdateCurvePoint(int id, int curveId, BigDecimal originalTerm, BigDecimal originalValue,
                                     BigDecimal newTerm, BigDecimal newValue) {
        // Given
        CurvePoint existingCurvePoint = new CurvePoint(curveId, originalTerm, originalValue);
        existingCurvePoint.setId(id);

        CurvePoint updatedCurvePoint = new CurvePoint(curveId, newTerm, newValue);
        updatedCurvePoint.setId(id);

        when(curvePointRepository.findById(id)).thenReturn(java.util.Optional.of(existingCurvePoint));

        // When
        curvePointService.update(updatedCurvePoint);

        // Then
        ArgumentCaptor<CurvePoint> captor = ArgumentCaptor.forClass(CurvePoint.class);
        verify(curvePointRepository).save(captor.capture());
        CurvePoint savedCurvePoint = captor.getValue();

        // Les champs term et value sont mis à jour
        assertEquals(newTerm, savedCurvePoint.getTerm());
        assertEquals(newValue, savedCurvePoint.getValue());


        assertEquals(id, savedCurvePoint.getId());
    }

    @Test
    public void testUpdateCurvePointShouldThrowExceptionWhenCurvePointNotFound() {
        // Given
        CurvePoint curvePointToUpdate = new CurvePoint(12, BigDecimal.valueOf(10.0), BigDecimal.valueOf(5.0));
        curvePointToUpdate.setId(999);

        when(curvePointRepository.findById(999)).thenReturn(java.util.Optional.empty());

        // When & Then
        assertThrows(EntityNotFoundException.class, () -> curvePointService.update(curvePointToUpdate));
    }

}
