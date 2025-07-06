package life.mosu.mosuserver.discount;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import life.mosu.mosuserver.domain.discount.FixedQuantityDiscountCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FixedQuantityDiscountCalculatorTest {

    private FixedQuantityDiscountCalculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new FixedQuantityDiscountCalculator();
    }

    @Test
    void 지원되는_회차에_대한_총_결제금액_계산() {
        assertEquals(49_000, calculator.calculateDiscount(1));
        assertEquals(89_000, calculator.calculateDiscount(2));
        assertEquals(129_000, calculator.calculateDiscount(3));
    }

    @Test
    void 지원되지_않는_회차_예외() {
        assertThrows(IllegalArgumentException.class, () -> calculator.calculateDiscount(0));
        assertThrows(IllegalArgumentException.class, () -> calculator.calculateDiscount(4));
        assertThrows(IllegalArgumentException.class, () -> calculator.calculateDiscount(-1));
    }

    @Test
    void 환불금액_계산_테스트_직접_차이_계산() {
        // 정상 케이스
        int originalQuantity = 3;
        int newQuantity = 2;
        int refundAmount =
                calculator.calculateDiscount(originalQuantity) - calculator.calculateDiscount(
                        newQuantity);
        assertEquals(40_000, refundAmount);

        originalQuantity = 3;
        newQuantity = 1;
        refundAmount =
                calculator.calculateDiscount(originalQuantity) - calculator.calculateDiscount(
                        newQuantity);
        assertEquals(80_000, refundAmount);

        originalQuantity = 2;
        newQuantity = 1;
        refundAmount =
                calculator.calculateDiscount(originalQuantity) - calculator.calculateDiscount(
                        newQuantity);
        assertEquals(40_000, refundAmount);
    }

    @Test
    void 환불금액_계산시_로직_내_수량_순서_검증_없음_주의() {
        // newQuantity가 originalQuantity보다 클 때도 계산은 됨 (비즈니스적으로는 잘못된 호출)
        int refundAmount = calculator.calculateDiscount(1) - calculator.calculateDiscount(2);
        // 49_000 - 89_000 = -40_000 환불액이 음수로 나옴 (잘못된 상황)
        assertEquals(-40_000, refundAmount);
    }
}
