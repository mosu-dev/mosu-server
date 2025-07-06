package life.mosu.mosuserver.domain.payment;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentAmountVO {

    @Column(name = "total_amount", nullable = false)
    private Integer totalAmount;

    @Column(name = "supplied_amount")
    private Integer suppliedAmount;

    @Column(name = "vat_amount")
    private Integer vatAmount;

    @Column(name = "balance_amount")
    private Integer balanceAmount;

    @Column(name = "tax_free_amount")
    private Integer taxFreeAmount;

    @Builder
    public PaymentAmountVO(
            Integer totalAmount,
            Integer suppliedAmount,
            Integer vatAmount,
            Integer balanceAmount,
            Integer taxFreeAmount
    ) {
        this.totalAmount = totalAmount;
        this.suppliedAmount = suppliedAmount;
        this.vatAmount = vatAmount;
        this.balanceAmount = balanceAmount;
        this.taxFreeAmount = taxFreeAmount;
    }

    public static PaymentAmountVO of(
            Integer totalAmount,
            Integer suppliedAmount,
            Integer vatAmount,
            Integer balanceAmount,
            Integer taxFreeAmount
    ) {
        return PaymentAmountVO.builder()
                .totalAmount(totalAmount)
                .suppliedAmount(suppliedAmount)
                .vatAmount(vatAmount)
                .balanceAmount(balanceAmount)
                .taxFreeAmount(taxFreeAmount)
                .build();
    }

    public static PaymentAmountVO ofFailure(Integer totalAmount) {
        return PaymentAmountVO.builder()
                .totalAmount(totalAmount)
                .build();
    }
}
