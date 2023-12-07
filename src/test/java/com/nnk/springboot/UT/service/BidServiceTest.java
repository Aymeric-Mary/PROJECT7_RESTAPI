package com.nnk.springboot.UT.service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.mapper.BidListMapper;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.services.BidService;
import com.nnk.springboot.utils.DateUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BidServiceTest {

    @Mock
    private BidListMapper bidListMapperMock;

    @Mock
    private BidListRepository bidListRepositoryMock;

    @InjectMocks
    private BidService bidListService;

    @Test
    void testCreate() {
        // Given
        BidList bidList = new BidList();
        when(bidListRepositoryMock.save(bidList)).thenReturn(bidList);
        // When
        BidList result = bidListService.create(bidList);
        // Then
        verify(bidListRepositoryMock).save(bidList);
        assertThat(result).isEqualTo(bidList);
    }

    @Test
    void testFindAll() {
        // Given
        List<BidList> bidLists = List.of(
                new BidList(),
                new BidList()
        );
        when(bidListRepositoryMock.findAll()).thenReturn(bidLists);
        // When
        List<BidList> result = bidListService.findAll();
        // Then
        verify(bidListRepositoryMock).findAll();
        assertThat(result).isEqualTo(bidLists);
    }

    @Test
    void testFindById() {
        // Given
        BidList bidList = new BidList();
        when(bidListRepositoryMock.findById(1)).thenReturn(Optional.of(bidList));
        // When
        Optional<BidList> result = bidListService.findById(1);
        // Then
        assertThat(result).isPresent().get().isEqualTo(bidList);
    }

    @Test
    void testUpdate() {
        // Given
        BidList existingBidList = BidList.builder()
                .id(1)
                .creationDate(Instant.parse("2021-04-24T10:15:30.00Z"))
                .account("account")
                .type("type")
                .bidQuantity(10.0)
                .build();
        BidList bidList = BidList.builder()
                .id(1)
                .account("new account")
                .type("new type")
                .bidQuantity(20.0)
                .build();
        when(bidListRepositoryMock.save(existingBidList)).thenReturn(existingBidList);
        doAnswer(invocation -> {
            BidList existing = invocation.getArgument(0, BidList.class);
            BidList updating = invocation.getArgument(1, BidList.class);
            existing.setAccount(updating.getAccount());
            existing.setType(updating.getType());
            existing.setBidQuantity(updating.getBidQuantity());
            return null;
        }).when(bidListMapperMock).updateBidList(existingBidList, bidList);
        // When
        try (MockedStatic<DateUtils> dateUtilsMock = mockStatic(DateUtils.class)) {
            dateUtilsMock.when(DateUtils::now).thenReturn(Instant.parse("2021-06-25T10:15:30.00Z"));
            BidList result = bidListService.update(existingBidList, bidList);
            // Then
            verify(bidListRepositoryMock).save(existingBidList);
            BidList expectedBidList = BidList
                    .builder()
                    .id(1)
                    .creationDate(Instant.parse("2021-04-24T10:15:30.00Z"))
                    .account("new account")
                    .type("new type")
                    .bidQuantity(20.0)
                    .build();
            assertThat(existingBidList)
                    .usingRecursiveComparison()
                    .isEqualTo(expectedBidList);
            assertThat(result).isEqualTo(existingBidList);
        }
    }

    @Test
    void deleteByIdTest() {
        // Given
        Integer id = 1;
        // When
        bidListService.deleteById(id);
        // Then
        verify(bidListRepositoryMock).deleteById(id);
    }


}
