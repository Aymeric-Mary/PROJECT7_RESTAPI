package com.nnk.springboot.UT.controllers.curvePoint;

import com.nnk.springboot.controllers.CurveController;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.CurvePointService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ShowAddCurvePointFormTest {

    @InjectMocks
    private CurveController curvePointController;

    @Mock
    private CurvePointService curvePointServiceMock;

    @Test
    void testShowAddForm() {
        // Given
        CurvePoint curvePoint = new CurvePoint();
        // When
        String viewName = curvePointController.addCurvePointForm(curvePoint);
        // Then
        assertThat(viewName).isEqualTo("curvePoint/add");
    }
}
