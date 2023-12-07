package com.nnk.springboot.UT.service;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.mapper.TradeMapper;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.services.TradeService;
import com.nnk.springboot.utils.DateUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TradeServiceTest {

    @Mock
    private TradeMapper tradeMapperMock;

    @Mock
    private TradeRepository tradeRepositoryMock;

    @InjectMocks
    private TradeService tradeService;

    @Test
    void testCreate() {
        // Given
        Trade trade = new Trade();
        when(tradeRepositoryMock.save(trade)).thenReturn(trade);
        // When
        Trade result = tradeService.create(trade);
        // Then
        verify(tradeRepositoryMock).save(trade);
        assertThat(result).isEqualTo(trade);
    }

    @Test
    void testFindAll() {
        // Given
        List<Trade> trades = List.of(
                new Trade(),
                new Trade()
        );
        when(tradeRepositoryMock.findAll()).thenReturn(trades);
        // When
        List<Trade> result = tradeService.findAll();
        // Then
        verify(tradeRepositoryMock).findAll();
        assertThat(result).isEqualTo(trades);
    }

    @Test
    void testFindById() {
        // Given
        Trade trade = new Trade();
        when(tradeRepositoryMock.findById(1)).thenReturn(Optional.of(trade));
        // When
        Optional<Trade> result = tradeService.findById(1);
        // Then
        assertThat(result).isPresent().get().isEqualTo(trade);
    }

    @Test
    void testUpdate() {
        // Given
        Trade existingTrade = Trade.builder()
                .id(1)
                .creationDate(Timestamp.from(Instant.parse("2021-04-24T10:15:30.00Z")))
                .account("account")
                .type("type")
                .buyQuantity(10.0)
                .build();
        Trade trade = Trade.builder()
                .id(1)
                .account("new account")
                .type("new type")
                .buyQuantity(20.0)
                .build();
        when(tradeRepositoryMock.save(existingTrade)).thenReturn(existingTrade);
        doAnswer(invocation -> {
            Trade existing = invocation.getArgument(0, Trade.class);
            Trade updating = invocation.getArgument(1, Trade.class);
            existing.setAccount(updating.getAccount());
            existing.setType(updating.getType());
            existing.setBuyQuantity(updating.getBuyQuantity());
            return null;
        }).when(tradeMapperMock).updateTrade(existingTrade, trade);
        // When
        try (MockedStatic<DateUtils> dateUtilsMock = mockStatic(DateUtils.class)) {
            dateUtilsMock.when(DateUtils::now).thenReturn(Instant.parse("2021-06-25T10:15:30.00Z"));
            Trade result = tradeService.update(existingTrade, trade);
            // Then
            verify(tradeRepositoryMock).save(existingTrade);
            Trade expectedTrade = Trade
                    .builder()
                    .id(1)
                    .creationDate(Timestamp.from(Instant.parse("2021-04-24T10:15:30.00Z")))
                    .revisionDate(Timestamp.from(Instant.parse("2021-06-25T10:15:30.00Z")))
                    .account("new account")
                    .type("new type")
                    .buyQuantity(20.0)
                    .build();
            assertThat(existingTrade)
                    .usingRecursiveComparison()
                    .isEqualTo(expectedTrade);
            assertThat(result).isEqualTo(existingTrade);
        }
    }

    @Test
    void deleteByIdTest(){
        // Given
        Integer id = 1;
        // When
        tradeService.deleteById(id);
        // Then
        verify(tradeRepositoryMock).deleteById(id);
    }


}
