package com.nnk.springboot.IT.curvePoint;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class DeleteCurvePointIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CurvePointRepository curvePointRepository;

    @Test
    public void testCurvePointDelete() throws Exception {
        // Given
        CurvePoint curvePoint = CurvePoint.builder()
                .curveId(1)
                .term(10.0)
                .value(15.0)
                .build();
        curvePointRepository.save(curvePoint);
        // When
        mockMvc.perform(get("/curvePoint/delete/" + curvePoint.getId()))
                // Then
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/curvePoint/list"));
        assertThat(curvePointRepository.findById(curvePoint.getId())).isEmpty();
    }

}
