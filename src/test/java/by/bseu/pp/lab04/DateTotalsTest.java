package by.bseu.pp.lab04;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static by.bseu.pp.lab04.TestData.*;

class DateTotalsTest {
    @Test
    void aggregatesByDateInSelectedPeriod() {
        Map<LocalDate, Double> result = SalesAnalyzer.amountByDateForPeriod(sales(), FROM, TO);
        assertEquals(Set.of(FROM, FROM.plusDays(1), FROM.plusDays(2), FROM.plusDays(3), TO), result.keySet());
        assertEquals(70.0, result.get(FROM), EPS);
        assertEquals(500.0, result.get(FROM.plusDays(1)), EPS);
        assertEquals(460.0, result.get(FROM.plusDays(2)), EPS);
        assertEquals(150.0, result.get(FROM.plusDays(3)), EPS);
        assertEquals(60.0, result.get(TO), EPS);
    }

    @Test
    void allowsOneDay() {
        Map<LocalDate, Double> result = SalesAnalyzer.amountByDateForPeriod(sales(), TO, TO);
        assertEquals(Set.of(TO), result.keySet());
        assertEquals(60.0, result.get(TO), EPS);
    }

    @Test
    void returnsEmptyForNoMatches() {
        assertTrue(SalesAnalyzer.amountByDateForPeriod(List.of(), FROM, TO).isEmpty());
        assertTrue(SalesAnalyzer.amountByDateForPeriod(sales(), FROM.plusYears(1), TO.plusYears(1)).isEmpty());
    }

    @Test
    void omitsMissingDaysKeepsZeroAndDoesNotRoundOrChangeSource() {
        List<Sale> input = new ArrayList<>(List.of(row("A", FROM, "Office", 0.105, 3),
                row("B", TO, "Office", 0, 1)));
        List<Sale> before = new ArrayList<>(input);
        Map<LocalDate, Double> result = SalesAnalyzer.amountByDateForPeriod(input, FROM, TO);
        assertEquals(Set.of(FROM, TO), result.keySet());
        assertEquals(0.315, result.get(FROM), EPS);
        assertEquals(0.0, result.get(TO), EPS);
        assertEquals(before, input);
        input.clear();
        assertEquals(2, result.size());
    }

    @Test
    void rejectsInvalidList() {
        assertThrows(IllegalArgumentException.class, () -> SalesAnalyzer.amountByDateForPeriod(null, FROM, TO));
        assertThrows(IllegalArgumentException.class, () -> SalesAnalyzer.amountByDateForPeriod(withNull(), FROM, TO));
    }

    @Test
    void rejectsInvalidPeriod() {
        assertThrows(IllegalArgumentException.class, () -> SalesAnalyzer.amountByDateForPeriod(sales(), null, TO));
        assertThrows(IllegalArgumentException.class, () -> SalesAnalyzer.amountByDateForPeriod(sales(), FROM, null));
        assertThrows(IllegalArgumentException.class, () -> SalesAnalyzer.amountByDateForPeriod(List.of(), TO, FROM));
    }

}
