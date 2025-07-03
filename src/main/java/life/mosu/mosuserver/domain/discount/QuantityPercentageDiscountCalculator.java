package life.mosu.mosuserver.domain.discount;

public class QuantityPercentageDiscountCalculator implements DiscountCalculator {

    private static final int PERCENT_DIVISOR = 100;
    private static final int ROUNDING_OFFSET = PERCENT_DIVISOR / 2;

    private static final int DISCOUNT_RATE_ONE = 10;
    private static final int DISCOUNT_RATE_TWO = 20;
    private static final int DISCOUNT_RATE_THREE_OR_MORE = 30;

    @Override
    public int calculateDiscount(int quantity, int unitPrice) {
        int discountRate = switch (quantity) {
            case 1 -> DISCOUNT_RATE_ONE;
            case 2 -> DISCOUNT_RATE_TWO;
            default -> DISCOUNT_RATE_THREE_OR_MORE;
        };

        int totalPrice = unitPrice * quantity;
        return (totalPrice * discountRate + ROUNDING_OFFSET) / PERCENT_DIVISOR;
    }
}
