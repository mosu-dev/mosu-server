package life.mosu.mosuserver.domain.discount;

public interface DiscountCalculator {
    int calculateDiscount(int quantity, int unitPrice);
}
