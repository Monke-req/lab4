package by.bseu.pp.lab04;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static by.bseu.pp.lab04.TestData.*;

class ProductSearchTest {
    @Test
    void findsAllMatches() {
        assertEquals(List.of("S01", "S02", "S06"), ids(SalesAnalyzer.findByProductCode(sales(), "P01")));
    }

    @Test
    void usesExactCaseSensitiveCode() {
        assertTrue(SalesAnalyzer.findByProductCode(sales(), "p01").isEmpty());
        assertTrue(SalesAnalyzer.findByProductCode(sales(), "P0").isEmpty());
    }

    @Test
    void handlesMissingProductAndEmptyList() {
        assertTrue(SalesAnalyzer.findByProductCode(sales(), "P99").isEmpty());
        assertTrue(SalesAnalyzer.findByProductCode(List.of(), "P01").isEmpty());
    }

    @Test
    void retainsOrderAndDoesNotChangeSource() {
        List<Sale> input = sales();
        Collections.reverse(input);
        List<Sale> before = new ArrayList<>(input);
        List<Sale> result = SalesAnalyzer.findByProductCode(input, "P01");
        assertEquals(List.of("S06", "S02", "S01"), ids(result));
        assertEquals(before, input);
        assertNotSame(input, result);
        input.clear();
        assertEquals(3, result.size());
    }

    @Test
    void rejectsInvalidCode() {
        assertThrows(IllegalArgumentException.class, () -> SalesAnalyzer.findByProductCode(sales(), null));
        assertThrows(IllegalArgumentException.class, () -> SalesAnalyzer.findByProductCode(sales(), ""));
        assertThrows(IllegalArgumentException.class, () -> SalesAnalyzer.findByProductCode(List.of(), "  "));
    }

    @Test
    void rejectsInvalidList() {
        assertThrows(IllegalArgumentException.class, () -> SalesAnalyzer.findByProductCode(null, "P01"));
        assertThrows(IllegalArgumentException.class, () -> SalesAnalyzer.findByProductCode(withNull(), "P01"));
    }

}
