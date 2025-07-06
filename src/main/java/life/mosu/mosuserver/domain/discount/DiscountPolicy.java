package life.mosu.mosuserver.domain.discount;

public enum DiscountPolicy {
    QUANTITY_PERCENTAGE(new QuantityPercentageDiscountCalculator()),
    FIXED_QUANTITY(new FixedQuantityDiscountCalculator());
    private final DiscountCalculator calculator;
    
    DiscountPolicy(DiscountCalculator calculator) {
        this.calculator = calculator;
    }

    public int calculateDiscount(int quantity) {
        return calculator.calculateDiscount(quantity);
    }
}