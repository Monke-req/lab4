package by.bseu.pp.lab04;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static by.bseu.pp.lab04.TestData.*;

class AmountSortTest {
    @Test
    void sortsByAmountNotPriceOrQuantity() {
        assertEquals(List.of("S07", "S05", "S04", "S10", "S08", "S01", "S06", "S09", "S02", "S03"),
                ids(SalesAnalyzer.sortByAmountDescending(sales())));
    }

    @Test
    void breaksTiesByIdAscending() {
        Sale a = row("A", FROM, "Office", 10, 2);
        Sale b = row("B", FROM, "Office", 20, 1);
        assertEquals(List.of("A", "B"), ids(SalesAnalyzer.sortByAmountDescending(List.of(b, a))));
    }

    @Test
    void handlesEmptyAndSingleton() {
        assertTrue(SalesAnalyzer.sortByAmountDescending(List.of()).isEmpty());
        Sale only = sales().get(0);
        assertEquals(List.of(only), SalesAnalyzer.sortByAmountDescending(List.of(only)));
    }

    @Test
    void doesNotChangeOrReuseSource() {
        List<Sale> input = sales();
        List<Sale> before = new ArrayList<>(input);
        List<Sale> result = SalesAnalyzer.sortByAmountDescending(input);
        assertEquals(before, input);
        assertNotSame(input, result);
        input.clear();
        assertEquals(10, result.size());
        assertEquals(10, SalesAnalyzer.sortByAmountDescending(List.copyOf(before)).size());
    }

    @Test
    void rejectsInvalidList() {
        assertThrows(IllegalArgumentException.class, () -> SalesAnalyzer.sortByAmountDescending(null));
        assertThrows(IllegalArgumentException.class, () -> SalesAnalyzer.sortByAmountDescending(withNull()));
    }

}
