package com.nnk.services;

import com.nnk.domain.Trade;
import com.nnk.exceptions.EntityNotFoundException;
import com.nnk.repositories.TradeRepository;
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

public class TradeServiceTest {


    @Mock
    private TradeRepository tradeRepository;

    @InjectMocks
    private TradeServiceImpl tradeService;

    @ParameterizedTest
    @CsvSource({"'Account1', 'Type1', 100.50",
            "'Account2', 'Type2', 200.75",
            "'Account3', 'Type3', 300.25"
    })
    public void testAddTrade(String account, String type, BigDecimal buyQuantity) {

        Trade trade = new Trade(account, type, buyQuantity);
        // When
        tradeService.create(trade);
        ArgumentCaptor<Trade> captor = ArgumentCaptor.forClass(Trade.class);

        // Then
        verify(tradeRepository).save(captor.capture());
        Trade savedTrade = captor.getValue();
        assertEquals(savedTrade.getAccount(), account);
        assertEquals(savedTrade.getType(), type);
        assertEquals(savedTrade.getBuyQuantity(), buyQuantity);


    }


    @Test
    public void testGetAllTrades() {
        //Given

        List<Trade> mockTrades = List.of(
                new Trade("Account1", "Type1", BigDecimal.valueOf(100.50)),
                new Trade("Account2", "Type2", BigDecimal.valueOf(200.75))
        );
        when(tradeRepository.findAll()).thenReturn(mockTrades);
        // When
        List<Trade> trades = tradeService.findAll();


        // Then
        assert (trades.size() == 2);
        assert (trades.get(0).getAccount().equals("Account1"));
        assert (trades.get(1).getType().equals("Type2"));
        assert (trades.get(0).getBuyQuantity().equals(BigDecimal.valueOf(100.50)));
        assert (trades.get(1).getAccount().equals("Account2"));


    }

    @Test
    public void testGetTradeById() {
        // Given
        Trade mockTrade = new Trade("Account1", "Type1", BigDecimal.valueOf(100.50));
        when(tradeRepository.findById(anyInt())).thenReturn(java.util.Optional.of(mockTrade));

        // When
        Trade trade = tradeService.findById(1);

        // Then
        assert (trade.getAccount().equals("Account1"));
        assert (trade.getType().equals("Type1"));
        assert (trade.getBuyQuantity().equals(BigDecimal.valueOf(100.50)));
    }


    @Test
    public void testGetTradeByIdShouldThrowExceptionWhenTradeNotFound() {
        // Given
        when(tradeRepository.findById(anyInt())).thenReturn(java.util.Optional.empty());

        // When & Then
        assertThrows(EntityNotFoundException.class, () -> tradeService.findById(999));

    }

    @ParameterizedTest
    @CsvSource({
            "1, 'Account1', 'Type1', 100.50, 'UpdatedAccount', 'UpdatedType', 150.75",
            "2, 'Account2', 'Type2', 200.75, 'UpdatedAccount2', 'UpdatedType2', 250.85",
            "3, 'Account3', 'Type3', 300.25, 'UpdatedAccount3', 'UpdatedType3', 350.95"
    })
    public void testUpdateTrade(int id, String originalAccount, String originalType, BigDecimal originalBuyQuantity,
                                String newAccount, String newType, BigDecimal newBuyQuantity) {
        // Given
        Trade existingTrade = new Trade(originalAccount, originalType, originalBuyQuantity);
        existingTrade.setTradeId(id);

        Trade updatedTrade = new Trade(newAccount, newType, newBuyQuantity);
        updatedTrade.setTradeId(id);

        when(tradeRepository.findById(id)).thenReturn(java.util.Optional.of(existingTrade));

        // When
        tradeService.update(updatedTrade);

        // Then
        ArgumentCaptor<Trade> captor = ArgumentCaptor.forClass(Trade.class);
        verify(tradeRepository).save(captor.capture());
        Trade savedTrade = captor.getValue();

        assertEquals(newAccount, savedTrade.getAccount());
        assertEquals(newType, savedTrade.getType());
        assertEquals(newBuyQuantity, savedTrade.getBuyQuantity());
        assertEquals(id, savedTrade.getTradeId());
    }

    @Test
    public void testUpdateTradeShouldThrowExceptionWhenTradeNotFound() {
        // Given
        Trade tradeToUpdate = new Trade("Account1", "Type1", BigDecimal.valueOf(100.50));
        tradeToUpdate.setTradeId(999);

        when(tradeRepository.findById(999)).thenReturn(java.util.Optional.empty());

        // When & Then
        assertThrows(EntityNotFoundException.class, () -> tradeService.update(tradeToUpdate));
    }

}
