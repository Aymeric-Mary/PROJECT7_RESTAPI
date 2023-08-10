package com.nnk.springboot.IT.curvePoint;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ShowUpdateFormIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CurvePointRepository curvePointRepository;

    @Test
    public void testShowUpdateFormWhenCurvePointExist() throws Exception {
        // Given
        CurvePoint curvePoint = CurvePoint.builder()
                .curveId(1)
                .term(10.0)
                .value(15.0)
                .build();
        curvePointRepository.save(curvePoint);
        // When
        mockMvc.perform(get("/curvePoint/update/" + curvePoint.getId()))
                // Then
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/update"))
                .andExpect(model().attributeExists("curvePoint"))
                .andExpect(model().attribute("curvePoint", curvePoint));
    }

    @Test
    public void testShowUpdateFormWhenCurvePointNotExist() throws Exception {
        // Given
        // When
        mockMvc.perform(get("/curvePoint/update/1234"))
                // Then
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/curvePoint/list"));
    }

}
