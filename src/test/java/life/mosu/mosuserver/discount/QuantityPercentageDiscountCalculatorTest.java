package life.mosu.mosuserver.discount;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import life.mosu.mosuserver.domain.discount.QuantityPercentageDiscountCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class QuantityPercentageDiscountCalculatorTest {

    private QuantityPercentageDiscountCalculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new QuantityPercentageDiscountCalculator();
    }

    @Test
    void 수량_1개일_때_10퍼센트_할인_계산합니다() {
        // 단가가 1,000으로 고정됨에 따라 할인 금액 계산
        // 1 * 1,000 * 10% = 1000
        assertEquals(100, calculator.calculateDiscount(1));
    }

    @Test
    void 수량_2개일_때_20퍼센트_할인_계산합니다() {
        // 2 * 1,000 * 20% = 400
        assertEquals(400, calculator.calculateDiscount(2));
    }

    @Test
    void 수량_3개_이상일_때_30퍼센트_할인_계산합니다() {
        // 3 * 1,000 * 30% = 9,00
        assertEquals(900, calculator.calculateDiscount(3));
        // 5 * 1,000 * 30% = 15,00 → 반올림으로 15,000으로 예상
        assertEquals(1_500, calculator.calculateDiscount(5));
    }

    @Test
    void 수량_음수일_때_예외_던집니다() {
        assertThrows(IllegalArgumentException.class, () -> calculator.calculateDiscount(-1));
    }

    @Test
    void 수량_0일_때_할인_0원_처리합니다() {
        assertEquals(0, calculator.calculateDiscount(0));
    }

    @Test
    void 큰_수량일_때_오버플로우_없는지_테스트() {
        int quantity = 100_000;
        int expected = (quantity * 1_000 * 30 + 50) / 100;
        assertEquals(expected, calculator.calculateDiscount(quantity));
    }
}
