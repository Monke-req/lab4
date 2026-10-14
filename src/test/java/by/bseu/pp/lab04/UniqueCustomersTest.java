package by.bseu.pp.lab04;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static by.bseu.pp.lab04.TestData.*;

class UniqueCustomersTest {
    @Test
    void collectsAllDistinctCustomers() {
        assertEquals(Set.of("C01", "C02", "C03", "C04"), SalesAnalyzer.uniqueCustomerCodes(sales()));
    }

    @Test
    void removesDuplicates() {
        Sale a = sales().get(0);
        assertEquals(Set.of("C01"), SalesAnalyzer.uniqueCustomerCodes(List.of(a, a)));
    }

    @Test
    void handlesEmptyList() {
        assertTrue(SalesAnalyzer.uniqueCustomerCodes(List.of()).isEmpty());
    }

    @Test
    void doesNotChangeSourceAndIsIndependent() {
        List<Sale> input = sales();
        List<Sale> before = new ArrayList<>(input);
        Set<String> result = SalesAnalyzer.uniqueCustomerCodes(input);
        assertEquals(before, input);
        input.clear();
        assertEquals(4, result.size());
    }

    @Test
    void rejectsInvalidList() {
        assertThrows(IllegalArgumentException.class, () -> SalesAnalyzer.uniqueCustomerCodes(null));
        assertThrows(IllegalArgumentException.class, () -> SalesAnalyzer.uniqueCustomerCodes(withNull()));
    }

}
