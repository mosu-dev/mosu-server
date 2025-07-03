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
        assertEquals(1_000, calculator.calculateDiscount(1, 10_000));
        assertEquals(1_000, calculator.calculateDiscount(1, 9_999));
        assertEquals(0, calculator.calculateDiscount(1, 1));
    }

    @Test
    void 수량_2개일_때_20퍼센트_할인_계산합니다() {
        assertEquals(4_000, calculator.calculateDiscount(2, 10_000));
        assertEquals(2_000, calculator.calculateDiscount(2, 4_999));
        assertEquals(0, calculator.calculateDiscount(2, 1));
    }

    @Test
    void 수량_3개_이상일_때_30퍼센트_할인_계산합니다() {
        assertEquals(9_000, calculator.calculateDiscount(3, 10_000));
        assertEquals(14_999, calculator.calculateDiscount(5, 9_999));
        assertEquals(3, calculator.calculateDiscount(10, 1));
    }

    @Test
    void 단가_음수일_때_예외_던집니다() {
        assertThrows(IllegalArgumentException.class, () -> calculator.calculateDiscount(1, -1_0000));
    }

    @Test
    void 수량_음수일_때_예외_던집니다() {
        assertThrows(IllegalArgumentException.class, () -> calculator.calculateDiscount(-1, 0));
    }

    @Test
    void 단가가_0원일_때_할인_0원_처리합니다() {
        assertEquals(0, calculator.calculateDiscount(1, 0));
        assertEquals(0, calculator.calculateDiscount(5, 0));
    }

    @Test
    void 큰_수량과_단가일_때_오버플로우_없는지_테스트() {
        int quantity = 100_000;
        int unitPrice = 1_000;
        int expected = (quantity * unitPrice * 30 + 50) / 100;
        assertEquals(expected, calculator.calculateDiscount(quantity, unitPrice));
    }
}

