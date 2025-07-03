package life.mosu.mosuserver.domain.discount;

public enum DiscountPolicy {
    QUANTITY_PERCENTAGE(new QuantityPercentageDiscountCalculator());

    private final DiscountCalculator calculator;

    DiscountPolicy(DiscountCalculator calculator) {
        this.calculator = calculator;
    }

    public int calculateDiscount(int quantity, int unitPrice) {
        return calculator.calculateDiscount(quantity, unitPrice);
    }
}