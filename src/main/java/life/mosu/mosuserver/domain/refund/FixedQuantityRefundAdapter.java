package life.mosu.mosuserver.domain.refund;

import life.mosu.mosuserver.domain.discount.DiscountCalculator;

public class FixedQuantityRefundAdapter implements RefundPolicyAdapter {

    private final DiscountCalculator discountCalculator;

    public FixedQuantityRefundAdapter(DiscountCalculator discountCalculator) {
        this.discountCalculator = discountCalculator;
    }

    @Override
    public int calculateRefund(int originalQty, int cancelQty) {
        int beforeDiscount = discountCalculator.calculateDiscount(originalQty);
        int afterDiscount = discountCalculator.calculateDiscount(originalQty - cancelQty);
        return beforeDiscount - afterDiscount;
    }
}
