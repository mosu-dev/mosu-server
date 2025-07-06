package life.mosu.mosuserver.domain.discount;

import java.util.Map;

public class FixedQuantityDiscountCalculator implements DiscountCalculator {

    // 회차별 고정 할인 가격 (총 결제 금액 기준)
    private static final Map<Integer, Integer> FIXED_TOTAL_PRICE = Map.of(
            1, 49_000,
            2, 89_000,
            3, 129_000
    );

    @Override
    public int calculateDiscount(int quantity) {
        if (!FIXED_TOTAL_PRICE.containsKey(quantity)) {
            throw new IllegalArgumentException("지원되지 않는 회차 수입니다. (1~3회만 가능)");
        }
        return FIXED_TOTAL_PRICE.get(quantity);
    }
}
