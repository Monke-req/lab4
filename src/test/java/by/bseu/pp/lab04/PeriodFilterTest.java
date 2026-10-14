package by.bseu.pp.lab04;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static by.bseu.pp.lab04.TestData.*;

class PeriodFilterTest {
    @Test
    void includesBothBoundaries() {
        assertEquals(List.of("S02", "S03", "S04", "S05", "S06", "S07", "S08", "S09"),
                ids(SalesAnalyzer.filterByPeriod(sales(), FROM, TO)));
    }

    @Test
    void allowsOneDay() {
        assertEquals(List.of("S04", "S05"),
                ids(SalesAnalyzer.filterByPeriod(sales(), FROM.plusDays(1), FROM.plusDays(1))));
    }

    @Test
    void returnsEmptyWhenNoMatches() {
        assertTrue(SalesAnalyzer.filterByPeriod(sales(), TO.plusYears(1), TO.plusYears(1)).isEmpty());
        assertTrue(SalesAnalyzer.filterByPeriod(List.of(), FROM, TO).isEmpty());
    }

    @Test
    void retainsInputOrder() {
        List<Sale> input = new ArrayList<>(sales());
        Collections.reverse(input);
        assertEquals(List.of("S09", "S08", "S07", "S06", "S05", "S04", "S03", "S02"),
                ids(SalesAnalyzer.filterByPeriod(input, FROM, TO)));
    }

    @Test
    void doesNotChangeSource() {
        List<Sale> input = sales();
        List<Sale> before = new ArrayList<>(input);
        List<Sale> result = SalesAnalyzer.filterByPeriod(input, FROM, TO);
        assertEquals(before, input);
        assertNotSame(input, result);
        input.clear();
        assertEquals(8, result.size());
    }

    @Test
    void rejectsInvalidList() {
        assertThrows(IllegalArgumentException.class, () -> SalesAnalyzer.filterByPeriod(null, FROM, TO));
        assertThrows(IllegalArgumentException.class, () -> SalesAnalyzer.filterByPeriod(withNull(), FROM, TO));
    }

    @Test
    void rejectsInvalidPeriod() {
        assertThrows(IllegalArgumentException.class, () -> SalesAnalyzer.filterByPeriod(sales(), null, TO));
        assertThrows(IllegalArgumentException.class, () -> SalesAnalyzer.filterByPeriod(sales(), FROM, null));
        assertThrows(IllegalArgumentException.class, () -> SalesAnalyzer.filterByPeriod(List.of(), TO, FROM));
    }

}
