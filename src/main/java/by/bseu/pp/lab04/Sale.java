package by.bseu.pp.lab04;

import java.time.LocalDate;

/** Готовая неизменяемая запись о продаже одной позиции товара. */
public final class Sale {
    private final String id;
    private final LocalDate date;
    private final String customerCode;
    private final String productCode;
    private final String category;
    private final double unitPrice;
    private final int quantity;

    public Sale(String id, LocalDate date, String customerCode,
                String productCode, String category, double unitPrice, int quantity) {
        requireText(id);
        requireText(customerCode);
        requireText(productCode);
        requireText(category);
        if (date == null || !Double.isFinite(unitPrice) || unitPrice < 0 || quantity <= 0) {
            throw new IllegalArgumentException("Некорректные данные продажи");
        }
        this.id = id;
        this.date = date;
        this.customerCode = customerCode;
        this.productCode = productCode;
        this.category = category;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    private static void requireText(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Строка должна быть непустой");
        }
    }

    public String getId() { return id; }
    public LocalDate getDate() { return date; }
    public String getCustomerCode() { return customerCode; }
    public String getProductCode() { return productCode; }
    public String getCategory() { return category; }
    public double getUnitPrice() { return unitPrice; }
    public int getQuantity() { return quantity; }
    public double getAmount() { return unitPrice * quantity; }
}
