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
    private Long totalAmount;

    @Column(name = "supplied_amount", nullable = false)
    private Long suppliedAmount;

    @Column(name = "vat_amount", nullable = false)
    private Long vatAmount;

    @Column(name = "balance_amount")
    private Long balanceAmount;

    @Column(name = "tax_free_amount")
    private Long taxFreeAmount;

    @Builder
    public PaymentAmountVO(
            Long totalAmount,
            Long suppliedAmount,
            Long vatAmount,
            Long balanceAmount,
            Long taxFreeAmount
    ) {
        this.totalAmount = totalAmount;
        this.suppliedAmount = suppliedAmount;
        this.vatAmount = vatAmount;
        this.balanceAmount = balanceAmount;
        this.taxFreeAmount = taxFreeAmount;
    }

    public static PaymentAmountVO of(
            Long totalAmount,
            Long suppliedAmount,
            Long vatAmount,
            Long balanceAmount,
            Long taxFreeAmount
    ) {
        return PaymentAmountVO.builder()
                .totalAmount(totalAmount)
                .suppliedAmount(suppliedAmount)
                .vatAmount(vatAmount)
                .balanceAmount(balanceAmount)
                .taxFreeAmount(taxFreeAmount)
                .build();
    }
}
