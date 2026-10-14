package by.bseu.pp.lab04;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

final class TestData {
    static final LocalDate FROM = LocalDate.of(2026, 9, 1);
    static final LocalDate TO = LocalDate.of(2026, 9, 5);
    static final double EPS = 1e-9;

    static List<Sale> sales() { return DemoData.createSales(); }
    static List<Sale> withNull() { return Arrays.asList(sales().get(0), null); }
    static List<String> ids(List<Sale> sales) {
        return sales.stream().map(Sale::getId).collect(Collectors.toList());
    }
    static Sale row(String id, LocalDate date, String category, double price, int quantity) {
        return new Sale(id, date, "C01", "P01", category, price, quantity);
    }
}
