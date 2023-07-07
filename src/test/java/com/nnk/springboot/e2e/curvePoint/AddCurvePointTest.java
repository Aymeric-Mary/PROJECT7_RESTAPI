package com.nnk.springboot.e2e.curvePoint;

import com.nnk.springboot.e2e.AbstractE2E;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.assertj.core.api.Assertions.assertThat;

public class AddCurvePointTest extends AbstractE2E {

    @Test
    public void testAddCurvePointWhenFieldsAreGood() {
        // Given
        driver.get(baseUrl + "/curvePoint/add");
        driver.findElement(By.id("curveId")).sendKeys("1");
        driver.findElement(By.id("term")).sendKeys("10");
        driver.findElement(By.id("value")).sendKeys("100.0");
        // When
        driver.findElement(By.cssSelector("input[type='submit']")).click();
        // Then
        wait.until(ExpectedConditions.urlToBe(baseUrl + "/curvePoint/list"));
        assertThat(driver.getCurrentUrl()).isEqualTo(baseUrl + "/curvePoint/list");
    }

    @Test
    public void testAddCurvePointWhenFieldsAreBad() {
        // Given
        driver.get(baseUrl + "/curvePoint/add");
        driver.findElement(By.id("curveId")).sendKeys("0");
        driver.findElement(By.id("term")).sendKeys("-1");
        driver.findElement(By.id("value")).sendKeys("-1");
        // When
        driver.findElement(By.cssSelector("input[type='submit']")).click();
        // Then
        wait.until(ExpectedConditions.urlToBe(baseUrl + "/curvePoint/validate"));
        assertThat(driver.getCurrentUrl()).isEqualTo(baseUrl + "/curvePoint/validate");
        String termError = driver.findElement(By.id("termError")).getText();
        String curveIdError = driver.findElement(By.id("curveIdError")).getText();
        String valueError = driver.findElement(By.id("valueError")).getText();
        assertThat(curveIdError).isEqualTo("must be greater than 0");
        assertThat(termError).isEqualTo("must be greater than or equal to 0");
        assertThat(valueError).isEqualTo("must be greater than or equal to 0");
    }
}
