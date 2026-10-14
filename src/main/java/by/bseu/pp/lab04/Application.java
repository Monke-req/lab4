package by.bseu.pp.lab04;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

/** Готовая демонстрация: запускайте зелёным треугольником рядом с main. */
public class Application {
    public static void main(String[] args) {
        List<Sale> sales = DemoData.createSales();
        LocalDate from = LocalDate.of(2026, 9, 1);
        LocalDate to = LocalDate.of(2026, 9, 5);
        System.out.println("Период: " + from + " — " + to);
        System.out.println("Продажи за период: " + ids(SalesAnalyzer.filterByPeriod(sales, from, to)));
        System.out.println("Продажи P01: " + ids(SalesAnalyzer.findByProductCode(sales, "P01")));
        System.out.println("Сортировка по сумме: " + ids(SalesAnalyzer.sortByAmountDescending(sales)));
        System.out.println("Покупатели: " + new TreeSet<>(SalesAnalyzer.uniqueCustomerCodes(sales)));
        Map<String, List<Sale>> groups = new TreeMap<>(SalesAnalyzer.groupByCategory(sales));
        groups.forEach((category, rows) ->
                System.out.println("Группа " + category + ": " + ids(rows)));
        System.out.printf(Locale.ROOT, "Всего за период: %.2f%n",
                SalesAnalyzer.totalAmountForPeriod(sales, from, to));
        new TreeMap<>(SalesAnalyzer.amountByCategoryForPeriod(sales, from, to))
                .forEach((category, amount) ->
                        System.out.printf(Locale.ROOT, "%s: %.2f%n", category, amount));
        new TreeMap<>(SalesAnalyzer.amountByDateForPeriod(sales, from, to))
                .forEach((date, amount) ->
                        System.out.printf(Locale.ROOT, "%s: %.2f%n", date, amount));
    }

    private static List<String> ids(List<Sale> sales) {
        return sales.stream().map(Sale::getId).collect(Collectors.toList());
    }
}
