package com.nnk.springboot.e2e.curvePoint;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.e2e.AbstractE2E;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ListCurvePointTest extends AbstractE2E {

    @Autowired
    CurvePointRepository curvePointRepository;

    @Test
    @Disabled
    public void testCurvePointListPage() throws InterruptedException {
        // Given
        List<CurvePoint> curvePoints = createCurvePoints();
        // When
        driver.get(baseUrl + "/curvePoint/list");
        // Then
        WebElement table = driver.findElement(By.className("table"));
        List<WebElement> tableRows = table.findElements(By.tagName("tr"));
        Thread.sleep(2000);
        assertThat(tableRows).hasSize(3);
        assertTableHeader(tableRows.get(0));
        assertTableRows(tableRows.subList(1, tableRows.size()), curvePoints);

    }

    private List<CurvePoint> createCurvePoints() {
        CurvePoint curvePoint1 = CurvePoint.builder().curveId(15).term(10.0).value(15.0).build();
        CurvePoint curvePoint2 = CurvePoint.builder().curveId(18).term(20.0).value(25.0).build();
        return List.of(
                curvePointRepository.save(curvePoint1),
                curvePointRepository.save(curvePoint2)
        );
    }

    private void assertTableHeader(WebElement headerRow) {
        assertThat(headerRow.findElements(By.tagName("th")))
                .extracting(WebElement::getText)
                .containsExactly("Id", "CurvePointId", "Term", "Value", "Action");
    }
    private void assertTableRows(List<WebElement> tableRows, List<CurvePoint> curvePoints) {
        assertThat(tableRows).hasSize(2);
        assertTableRowValues(tableRows.get(0), curvePoints.get(0).getId().toString(), "15", "10.0", "15.0", "Edit |  Delete");
        assertTableRowValues(tableRows.get(1), curvePoints.get(1).getId().toString(), "18", "20.0", "25.0", "Edit |  Delete");
        assertThat(tableRows).allMatch(this::rowHasCorrectLinks);
    }

    private void assertTableRowValues(WebElement tableRow, String id, String curveId, String term, String value, String action) {
        assertThat(tableRow.findElements(By.tagName("td")))
                .extracting(WebElement::getText)
                .containsSequence(id, curveId, term, value, action);
    }

    private boolean rowHasCorrectLinks(WebElement tableRow) {
        String id = tableRow.findElement(By.tagName("td")).getText();
        try {
            String updateHref = new URI(tableRow.findElements(By.tagName("a")).get(0).getAttribute("href")).getPath();
            String deleteHref = new URI(tableRow.findElements(By.tagName("a")).get(1).getAttribute("href")).getPath();
            return ("/curvePoint/update/" + id).equals(updateHref) &&
                    ("/curvePoint/delete/" + id).equals(deleteHref);
        } catch (URISyntaxException e) {
            return false;
        }
    }
}
