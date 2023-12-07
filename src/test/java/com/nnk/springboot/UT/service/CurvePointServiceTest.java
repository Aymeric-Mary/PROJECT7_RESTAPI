package com.nnk.springboot.UT.service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.mapper.CurvePointMapper;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.services.CurvePointService;
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
class CurvePointServiceTest {

    @Mock
    private CurvePointMapper curvePointMapperMock;

    @Mock
    private CurvePointRepository curvePointRepositoryMock;

    @InjectMocks
    private CurvePointService curvePointService;

    @Test
    void testCreate() {
        // Given
        CurvePoint curvePoint = new CurvePoint();
        when(curvePointRepositoryMock.save(curvePoint)).thenReturn(curvePoint);
        // When
        CurvePoint result = curvePointService.create(curvePoint);
        // Then
        verify(curvePointRepositoryMock).save(curvePoint);
        assertThat(result).isEqualTo(curvePoint);
    }

    @Test
    void testFindAll() {
        // Given
        List<CurvePoint> curvePoints = List.of(
                new CurvePoint(),
                new CurvePoint()
        );
        when(curvePointRepositoryMock.findAll()).thenReturn(curvePoints);
        // When
        List<CurvePoint> result = curvePointService.findAll();
        // Then
        verify(curvePointRepositoryMock).findAll();
        assertThat(result).isEqualTo(curvePoints);
    }

    @Test
    void testFindById() {
        // Given
        CurvePoint curvePoint = new CurvePoint();
        when(curvePointRepositoryMock.findById(1)).thenReturn(Optional.of(curvePoint));
        // When
        Optional<CurvePoint> result = curvePointService.findById(1);
        // Then
        assertThat(result).isPresent().get().isEqualTo(curvePoint);
    }

    @Test
    void testUpdate() {
        // Given
        CurvePoint existingCurvePoint = CurvePoint.builder()
                .id(1)
                .creationDate(Instant.parse("2021-04-24T10:15:30.00Z"))
                .curveId(10)
                .term(10.0)
                .value(15.0)
                .build();
        CurvePoint curvePoint = CurvePoint.builder()
                .id(1)
                .curveId(20)
                .term(20.0)
                .value(25.0)
                .build();
        when(curvePointRepositoryMock.save(existingCurvePoint)).thenReturn(existingCurvePoint);
        doAnswer(invocation -> {
            CurvePoint existing = invocation.getArgument(0, CurvePoint.class);
            CurvePoint updating = invocation.getArgument(1, CurvePoint.class);
            existing.setCurveId(updating.getCurveId());
            existing.setTerm(updating.getTerm());
            existing.setValue(updating.getValue());
            return null;
        }).when(curvePointMapperMock).updateCurvePoint(existingCurvePoint, curvePoint);
        // When
        try (MockedStatic<DateUtils> dateUtilsMock = mockStatic(DateUtils.class)) {
            dateUtilsMock.when(DateUtils::now).thenReturn(Instant.parse("2021-06-25T10:15:30.00Z"));
            CurvePoint result = curvePointService.update(existingCurvePoint, curvePoint);
            // Then
            verify(curvePointRepositoryMock).save(existingCurvePoint);
            CurvePoint expectedCurvePoint = CurvePoint
                    .builder()
                    .id(1)
                    .creationDate(Instant.parse("2021-04-24T10:15:30.00Z"))
                    .asOfDate(Instant.parse("2021-06-25T10:15:30.00Z"))
                    .curveId(20)
                    .term(20.0)
                    .value(25.0)
                    .build();
            assertThat(existingCurvePoint)
                    .usingRecursiveComparison()
                    .isEqualTo(expectedCurvePoint);
            assertThat(result).isEqualTo(existingCurvePoint);
        }
    }

    @Test
    void deleteByIdTest(){
        // Given
        Integer id = 1;
        // When
        curvePointService.deleteById(id);
        // Then
        verify(curvePointRepositoryMock).deleteById(id);
    }


}
