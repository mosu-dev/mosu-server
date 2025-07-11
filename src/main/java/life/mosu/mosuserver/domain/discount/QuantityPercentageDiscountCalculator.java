package life.mosu.mosuserver.domain.discount;

public class QuantityPercentageDiscountCalculator implements DiscountCalculator {

    private static final int PERCENT_DIVISOR = 100;
    private static final int ROUNDING_OFFSET = PERCENT_DIVISOR / 2;

    private static final int DISCOUNT_RATE_ONE = 10;
    private static final int DISCOUNT_RATE_TWO = 20;
    private static final int DISCOUNT_RATE_THREE_OR_MORE = 30;
    private static final int UNIT_PRICE = 1_000;

    @Override
    public int calculateDiscount(int quantity) {
        validateInputs(quantity);

        int discountRate = determineDiscountRate(quantity);
        int totalPrice = calculateTotalPrice(quantity, UNIT_PRICE);

        return calculateDiscountAmount(totalPrice, discountRate);
    }

    private void validateInputs(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("수량(quantity)은 음수일 수 없습니다: " + quantity);
        }
        if (QuantityPercentageDiscountCalculator.UNIT_PRICE < 0) {
            throw new IllegalArgumentException("단가(unitPrice)는 음수일 수 없습니다: "
                    + QuantityPercentageDiscountCalculator.UNIT_PRICE);
        }
    }

    private int determineDiscountRate(int quantity) {
        return switch (quantity) {
            case 1 -> DISCOUNT_RATE_ONE;
            case 2 -> DISCOUNT_RATE_TWO;
            default -> DISCOUNT_RATE_THREE_OR_MORE;
        };
    }

    private int calculateTotalPrice(int quantity, int unitPrice) {
        return quantity * unitPrice;
    }

    private int calculateDiscountAmount(int totalPrice, int discountRate) {
        return (totalPrice * discountRate + ROUNDING_OFFSET) / PERCENT_DIVISOR;
    }
}
