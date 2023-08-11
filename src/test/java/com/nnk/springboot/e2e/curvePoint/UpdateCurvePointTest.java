package com.nnk.springboot.e2e.curvePoint;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.e2e.AbstractE2E;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

public class UpdateCurvePointTest extends AbstractE2E {

    @Autowired
    CurvePointRepository curvePointRepository;

    @Test
    public void testShowUpdateFormWhenCurvePointExist() {
        // Given
        CurvePoint curvePoint = CurvePoint
                .builder()
                .creationDate(Instant.parse("2023-07-25T10:00:00Z"))
                .asOfDate(Instant.parse("2023-07-25T10:00:00Z"))
                .curveId(15)
                .term(10.0)
                .value(15.0)
                .build();
        curvePointRepository.save(curvePoint);
        // When
        driver.get(baseUrl + "/curvePoint/update/" + curvePoint.getId());
        // Then
        WebElement curveIdInput = driver.findElement(By.id("curveId"));
        WebElement termInput = driver.findElement(By.id("term"));
        WebElement valueInput = driver.findElement(By.id("value"));
        WebElement submitButton = driver.findElement(By.cssSelector("input[type='submit']"));
        WebElement cancelButton = driver.findElement(By.id("cancel"));
        assertThat(curveIdInput.getAttribute("value")).isEqualTo("15");
        assertThat(termInput.getAttribute("value")).isEqualTo("10.0");
        assertThat(valueInput.getAttribute("value")).isEqualTo("15.0");
        assertThat(submitButton.getAttribute("value")).isEqualTo("Update Curve Point");
        assertThat(cancelButton.getText()).isEqualTo("Cancel");
        // When
        curveIdInput.clear();
        curveIdInput.sendKeys("5");
        termInput.clear();
        termInput.sendKeys("20.0");
        valueInput.clear();
        valueInput.sendKeys("25.0");
        submitButton.click();
        wait.until(ExpectedConditions.urlToBe(baseUrl + "/curvePoint/list"));
        // Then
        CurvePoint expected = CurvePoint.builder()
                .id(curvePoint.getId())
                .creationDate(Instant.parse("2023-07-25T10:00:00Z"))
                .curveId(5)
                .term(20.0)
                .value(25.0)
                .build();
        assertThat(curvePointRepository.findAll())
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("asOfDate")
                .containsExactly(expected);
    }
}
