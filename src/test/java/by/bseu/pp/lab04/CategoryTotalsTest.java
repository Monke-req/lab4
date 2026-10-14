package by.bseu.pp.lab04;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static by.bseu.pp.lab04.TestData.*;

class CategoryTotalsTest {
    @Test
    void aggregatesByCategoryInSelectedPeriod() {
        Map<String, Double> result = SalesAnalyzer.amountByCategoryForPeriod(sales(), FROM, TO);
        assertEquals(Set.of("Office", "Electronics", "Furniture"), result.keySet());
        assertEquals(190.0, result.get("Office"), EPS);
        assertEquals(600.0, result.get("Electronics"), EPS);
        assertEquals(450.0, result.get("Furniture"), EPS);
    }

    @Test
    void includesSingleDayAndOmitsAbsentCategories() {
        Map<String, Double> result = SalesAnalyzer.amountByCategoryForPeriod(sales(), FROM, FROM);
        assertEquals(Set.of("Office"), result.keySet());
        assertEquals(70.0, result.get("Office"), EPS);
    }

    @Test
    void returnsEmptyForNoMatches() {
        assertTrue(SalesAnalyzer.amountByCategoryForPeriod(List.of(), FROM, TO).isEmpty());
        assertTrue(SalesAnalyzer.amountByCategoryForPeriod(sales(), FROM.plusYears(1), TO.plusYears(1)).isEmpty());
    }

    @Test
    void keepsZeroCategoryDoesNotRoundOrChangeSource() {
        List<Sale> input = new ArrayList<>(List.of(row("A", FROM, "Office", 0.105, 3),
                row("B", TO, "Other", 0, 1)));
        List<Sale> before = new ArrayList<>(input);
        Map<String, Double> result = SalesAnalyzer.amountByCategoryForPeriod(input, FROM, TO);
        assertEquals(Set.of("Office", "Other"), result.keySet());
        assertEquals(0.315, result.get("Office"), EPS);
        assertEquals(0.0, result.get("Other"), EPS);
        assertEquals(before, input);
        input.clear();
        assertEquals(2, result.size());
    }

    @Test
    void rejectsInvalidList() {
        assertThrows(IllegalArgumentException.class, () -> SalesAnalyzer.amountByCategoryForPeriod(null, FROM, TO));
        assertThrows(IllegalArgumentException.class, () -> SalesAnalyzer.amountByCategoryForPeriod(withNull(), FROM, TO));
    }

    @Test
    void rejectsInvalidPeriod() {
        assertThrows(IllegalArgumentException.class, () -> SalesAnalyzer.amountByCategoryForPeriod(sales(), null, TO));
        assertThrows(IllegalArgumentException.class, () -> SalesAnalyzer.amountByCategoryForPeriod(sales(), FROM, null));
        assertThrows(IllegalArgumentException.class, () -> SalesAnalyzer.amountByCategoryForPeriod(List.of(), TO, FROM));
    }

}
