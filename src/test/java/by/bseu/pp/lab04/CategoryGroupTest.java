package by.bseu.pp.lab04;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static by.bseu.pp.lab04.TestData.*;

class CategoryGroupTest {
    @Test
    void groupsAllSalesAndRetainsOrderInsideGroups() {
        Map<String, List<Sale>> result = SalesAnalyzer.groupByCategory(sales());
        assertEquals(Set.of("Office", "Electronics", "Furniture"), result.keySet());
        assertEquals(List.of("S01", "S02", "S03", "S06", "S09"), ids(result.get("Office")));
        assertEquals(List.of("S04", "S07", "S10"), ids(result.get("Electronics")));
        assertEquals(List.of("S05", "S08"), ids(result.get("Furniture")));
    }

    @Test
    void retainsRepeatedRecords() {
        Sale a = sales().get(0);
        assertEquals(List.of(a, a), SalesAnalyzer.groupByCategory(List.of(a, a)).get("Office"));
    }

    @Test
    void handlesEmptyList() {
        assertTrue(SalesAnalyzer.groupByCategory(List.of()).isEmpty());
    }

    @Test
    void doesNotChangeSourceAndGroupsAreIndependent() {
        List<Sale> input = sales();
        List<Sale> before = new ArrayList<>(input);
        Map<String, List<Sale>> result = SalesAnalyzer.groupByCategory(input);
        assertEquals(before, input);
        assertNotSame(input, result.get("Office"));
        input.clear();
        assertEquals(5, result.get("Office").size());
    }

    @Test
    void rejectsInvalidList() {
        assertThrows(IllegalArgumentException.class, () -> SalesAnalyzer.groupByCategory(null));
        assertThrows(IllegalArgumentException.class, () -> SalesAnalyzer.groupByCategory(withNull()));
    }

}
