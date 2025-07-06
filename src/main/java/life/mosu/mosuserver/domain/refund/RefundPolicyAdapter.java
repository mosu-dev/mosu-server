package life.mosu.mosuserver.domain.refund;

public interface RefundPolicyAdapter {

    int calculateRefund(int originalQty, int cancelQty);
}