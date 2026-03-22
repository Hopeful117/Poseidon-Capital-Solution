package com.nnk.services;

import com.nnk.domain.BidList;
import com.nnk.exceptions.EntityNotFoundException;
import com.nnk.repositories.BidListRepository;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)

public class BidListServiceTest {


    @Mock
    private BidListRepository bidListRepository;

    @InjectMocks
    private BidListServiceImpl bidListService;

    @ParameterizedTest
    @CsvSource({"'Account1', 'Type1', 100.0",
            "'Account2', 'Type2', 200.0",
            "'Account3', 'Type3', 300.0"
    })
    public void testAddBidList(String account, String type, BigDecimal bidQuantity) {

        BidList bidList = new BidList(account, type, bidQuantity);
        // When
        bidListService.create(bidList);
        ArgumentCaptor<BidList> captor = ArgumentCaptor.forClass(BidList.class);

        // Then
        verify(bidListRepository).save(captor.capture());
        BidList savedBidList = captor.getValue();
        assertEquals(savedBidList.getAccount(), account);
        assertEquals(savedBidList.getType(), type);
        assertEquals(savedBidList.getBidQuantity(), bidQuantity);


    }


    @Test
    public void testGetAllBidLists() {
        //Given

        List<BidList> mockBidLists = List.of(
                new BidList("Account1", "Type1", BigDecimal.valueOf(100)),
                new BidList("Account2", "Type2", BigDecimal.valueOf(200))
        );
        when(bidListRepository.findAll()).thenReturn(mockBidLists);
        // When
        List<BidList> bidLists = bidListService.findAll();


        // Then
        assert (bidLists.size() == 2);
        assert (bidLists.get(0).getAccount().equals("Account1"));
        assert (bidLists.get(1).getType().equals("Type2"));
        assert (bidLists.get(0).getBidQuantity().equals(BigDecimal.valueOf(100)));
        assert (bidLists.get(1).getBidListId() == null); // or something, but since not set


    }

    @Test
    public void testGetBidListById() {
        // Given
        BidList mockBidList = new BidList("Account1", "Type1", BigDecimal.valueOf(100));
        when(bidListRepository.findById(anyInt())).thenReturn(java.util.Optional.of(mockBidList));

        // When
        BidList bidList = bidListService.findById(1);

        // Then
        assert (bidList.getAccount().equals("Account1"));
        assert (bidList.getType().equals("Type1"));
        assert (bidList.getBidQuantity().equals(BigDecimal.valueOf(100)));
    }



    @Test
    public void testGetBidListByIdShouldThrowExceptionWhenBidListNotFound() {
        // Given

        when(bidListRepository.findById(anyInt())).thenReturn(java.util.Optional.empty());

        // When & Then
        assertThrows(EntityNotFoundException.class, () -> bidListService.findById(999));

    }

    @ParameterizedTest
    @CsvSource({
        "1, 'Account1', 'Type1', 100.0, 'UpdatedAccount', 'UpdatedType', 150.0",
        "2, 'Account2', 'Type2', 200.0, 'UpdatedAccount2', 'UpdatedType2', 250.0",
        "3, 'Account3', 'Type3', 300.0, 'UpdatedAccount3', 'UpdatedType3', 350.0"
    })
    public void testUpdateBidList(int id, String originalAccount, String originalType, BigDecimal originalBidQuantity,
                                 String newAccount, String newType, BigDecimal newBidQuantity) {
        // Given
        BidList existingBidList = new BidList(originalAccount, originalType, originalBidQuantity);
        existingBidList.setBidListId(id);
        
        BidList updatedBidList = new BidList(newAccount, newType, newBidQuantity);
        updatedBidList.setBidListId(id);
        
        when(bidListRepository.findById(id)).thenReturn(java.util.Optional.of(existingBidList));

        // When
        bidListService.update(updatedBidList);

        // Then
        ArgumentCaptor<BidList> captor = ArgumentCaptor.forClass(BidList.class);
        verify(bidListRepository).save(captor.capture());
        BidList savedBidList = captor.getValue();
        
        assertEquals(newAccount, savedBidList.getAccount());
        assertEquals(newType, savedBidList.getType());
        assertEquals(newBidQuantity, savedBidList.getBidQuantity());
        assertEquals(id, savedBidList.getId());
    }

    @Test
    public void testUpdateBidListShouldThrowExceptionWhenBidListNotFound() {
        // Given
        BidList bidListToUpdate = new BidList("Account", "Type", BigDecimal.valueOf(100));
        bidListToUpdate.setBidListId(999);
        
        when(bidListRepository.findById(999)).thenReturn(java.util.Optional.empty());

        // When & Then
        assertThrows(EntityNotFoundException.class, () -> bidListService.update(bidListToUpdate));
    }

}
