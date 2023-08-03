package com.nnk.springboot.e2e.curvePoint;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.e2e.AbstractE2E;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ListCurvePointTest extends AbstractE2E {

    @Autowired
    CurvePointRepository curvePointRepository;

    @Test
    public void testCurvePointListPage() {
        // Given
        CurvePoint curvePoint1 = CurvePoint.builder()
                .curveId(15)
                .term(10.0)
                .value(15.0)
                .build();
        CurvePoint curvePoint2 = CurvePoint.builder()
                .curveId(18)
                .term(20.0)
                .value(25.0)
                .build();
        curvePointRepository.save(curvePoint1);
        curvePointRepository.save(curvePoint2);
        // When
        driver.get(baseUrl + "/curvePoint/list");
        // Then
        WebElement table = driver.findElement(By.className("table"));
        List<WebElement> tableRows = table.findElements(By.tagName("tr"));
        assertThat(tableRows).hasSize(3);
        WebElement head = tableRows.get(0);
        assertThat(head.findElements(By.tagName("th")))
                .extracting(WebElement::getText)
                .containsExactly("Id", "CurvePointId", "Term", "Value", "Action");
        WebElement row1 = tableRows.get(1);
        assertThat(row1.findElements(By.tagName("td")))
                .extracting(WebElement::getText)
                .containsExactly(curvePoint1.getId().toString(), "15", "10.0", "15.0", "Edit |  Delete");
        WebElement row2 = tableRows.get(2);
        assertThat(row2.findElements(By.tagName("td")))
                .extracting(WebElement::getText)
                .containsExactly(curvePoint2.getId().toString(), "18", "20.0", "25.0", "Edit |  Delete");

        assertThat(tableRows.subList(1, tableRows.size()))
                .allMatch(tableRow -> {
                    String id = tableRow.findElement(By.tagName("td")).getText();
                    return ("/curvePoint/update/" + id).equals(tableRow.findElements(By.tagName("a")).get(0).getAttribute("href")) &&
                            ("/curvePoint/delete/" + id).equals(tableRow.findElements(By.tagName("a")).get(1).getAttribute("href"));
                });

    }
}
