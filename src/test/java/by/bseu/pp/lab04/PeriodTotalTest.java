package by.bseu.pp.lab04;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static by.bseu.pp.lab04.TestData.*;

class PeriodTotalTest {
    @Test
    void sumsOnlySelectedPeriod() {
        assertEquals(1240.0, SalesAnalyzer.totalAmountForPeriod(sales(), FROM, TO), EPS);
        assertEquals(1540.0, SalesAnalyzer.totalAmountForPeriod(sales(), FROM.minusDays(1), TO.plusDays(1)), EPS);
    }

    @Test
    void allowsOneDayAndIncludesBothBoundaries() {
        assertEquals(70.0, SalesAnalyzer.totalAmountForPeriod(sales(), FROM, FROM), EPS);
        assertEquals(60.0, SalesAnalyzer.totalAmountForPeriod(sales(), TO, TO), EPS);
    }

    @Test
    void returnsZeroForNoMatches() {
        assertEquals(0.0, SalesAnalyzer.totalAmountForPeriod(List.of(), FROM, TO), EPS);
        assertEquals(0.0, SalesAnalyzer.totalAmountForPeriod(sales(), FROM.plusYears(1), TO.plusYears(1)), EPS);
    }

    @Test
    void doesNotRoundAndDoesNotChangeSource() {
        List<Sale> input = new ArrayList<>(List.of(row("A", FROM, "Office", 0.105, 3)));
        List<Sale> before = new ArrayList<>(input);
        assertEquals(0.315, SalesAnalyzer.totalAmountForPeriod(input, FROM, TO), EPS);
        assertEquals(before, input);
    }

    @Test
    void rejectsInvalidList() {
        assertThrows(IllegalArgumentException.class, () -> SalesAnalyzer.totalAmountForPeriod(null, FROM, TO));
        assertThrows(IllegalArgumentException.class, () -> SalesAnalyzer.totalAmountForPeriod(withNull(), FROM, TO));
    }

    @Test
    void rejectsInvalidPeriod() {
        assertThrows(IllegalArgumentException.class, () -> SalesAnalyzer.totalAmountForPeriod(sales(), null, TO));
        assertThrows(IllegalArgumentException.class, () -> SalesAnalyzer.totalAmountForPeriod(sales(), FROM, null));
        assertThrows(IllegalArgumentException.class, () -> SalesAnalyzer.totalAmountForPeriod(List.of(), TO, FROM));
    }

}
