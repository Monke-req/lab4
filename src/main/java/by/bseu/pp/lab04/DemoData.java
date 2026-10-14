package by.bseu.pp.lab04;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/** Готовые учебные данные. Каждый вызов возвращает новый список. */
public final class DemoData {
    private DemoData() { }

    public static List<Sale> createSales() {
        return new ArrayList<>(List.of(
            sale("S01", "2026-08-31", "C01", "P01", "Office", 20, 5),
            sale("S02", "2026-09-01", "C01", "P01", "Office", 20, 2),
            sale("S03", "2026-09-01", "C02", "P02", "Office", 15, 2),
            sale("S04", "2026-09-02", "C01", "P03", "Electronics", 200, 1),
            sale("S05", "2026-09-02", "C03", "P04", "Furniture", 150, 2),
            sale("S06", "2026-09-03", "C02", "P01", "Office", 20, 3),
            sale("S07", "2026-09-03", "C03", "P03", "Electronics", 200, 2),
            sale("S08", "2026-09-04", "C04", "P04", "Furniture", 150, 1),
            sale("S09", "2026-09-05", "C04", "P02", "Office", 15, 4),
            sale("S10", "2026-09-06", "C01", "P03", "Electronics", 200, 1)
        ));
    }

    private static Sale sale(String id, String date, String customer, String product,
                             String category, double price, int quantity) {
        return new Sale(id, LocalDate.parse(date), customer, product, category, price, quantity);
    }
}
