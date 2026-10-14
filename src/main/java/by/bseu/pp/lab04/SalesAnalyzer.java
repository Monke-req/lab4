package by.bseu.pp.lab04;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public final class SalesAnalyzer {
    private SalesAnalyzer() { }

    // Вспомогательный метод для полной валидации списка и его содержимого
    private static void checkSales(List<Sale> sales) {
        if (sales == null) {
            throw new IllegalArgumentException("Список продаж не может быть null");
        }
        // Если внутри списка есть хотя бы один null-элемент — бросаем корректное исключение
        for (Sale sale : sales) {
            if (sale == null) {
                throw new IllegalArgumentException("Элемент списка не может быть null");
            }
        }
    }

    private static void checkPeriod(LocalDate from, LocalDate to) {
        if (from == null || to == null || from.isAfter(to)) {
            throw new IllegalArgumentException("Некорректные границы периода");
        }
    }

    /** 5.1 Продажи за период включительно. */
    public static List<Sale> filterByPeriod(List<Sale> sales, LocalDate from, LocalDate to) {
        checkSales(sales);
        checkPeriod(from, to);
        return sales.stream()
                .filter(sale -> !sale.getDate().isBefore(from) && !sale.getDate().isAfter(to))
                .collect(Collectors.toList());
    }

    /** 5.2 Все продажи с точным совпадением кода товара. */
    public static List<Sale> findByProductCode(List<Sale> sales, String productCode) {
        checkSales(sales);
        if (productCode == null || productCode.isBlank()) {
            throw new IllegalArgumentException("Код товара не может быть пустым");
        }
        List<Sale> result = new ArrayList<>();
        for (Sale sale : sales) {
            if (sale.getProductCode().equals(productCode)) {
                result.add(sale);
            }
        }
        return result;
    }

    /** 5.3 Сортировка по сумме по убыванию. */
    public static List<Sale> sortByAmountDescending(List<Sale> sales) {
        checkSales(sales);
        return sales.stream()
                .sorted(Comparator.comparingDouble(Sale::getAmount).reversed()
                        .thenComparing(Sale::getId))
                .collect(Collectors.toList());
    }

    /** 6.1 Множество уникальных кодов покупателей. */
    public static Set<String> uniqueCustomerCodes(List<Sale> sales) {
        checkSales(sales);
        Set<String> result = new HashSet<>();
        for (Sale sale : sales) {
            result.add(sale.getCustomerCode());
        }
        return result;
    }

    /** 6.2 Группировка продаж по категориям. */
    public static Map<String, List<Sale>> groupByCategory(List<Sale> sales) {
        checkSales(sales);
        return sales.stream()
                .collect(Collectors.groupingBy(Sale::getCategory));
    }

    /** 7.1 Общая сумма продаж за заданный период. */
    public static double totalAmountForPeriod(List<Sale> sales, LocalDate from, LocalDate to) {
        checkSales(sales);
        checkPeriod(from, to);
        return filterByPeriod(sales, from, to).stream()
                .mapToDouble(Sale::getAmount)
                .sum();
    }

    /** 7.2 Сумма продаж по категориям за период. */
    public static Map<String, Double> amountByCategoryForPeriod(List<Sale> sales, LocalDate from, LocalDate to) {
        checkSales(sales);
        checkPeriod(from, to);
        return filterByPeriod(sales, from, to).stream()
                .collect(Collectors.groupingBy(
                        Sale::getCategory,
                        Collectors.summingDouble(Sale::getAmount)
                ));
    }

    /** 7.3 Сумма продаж по дням за период. */
    public static Map<LocalDate, Double> amountByDateForPeriod(List<Sale> sales, LocalDate from, LocalDate to) {
        checkSales(sales);
        checkPeriod(from, to);
        return filterByPeriod(sales, from, to).stream()
                .collect(Collectors.groupingBy(
                        Sale::getDate,
                        Collectors.summingDouble(Sale::getAmount)
                ));
    }
}
