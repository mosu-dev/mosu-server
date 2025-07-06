package life.mosu.mosuserver.domain.payment;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<PaymentJpaEntity, Long>{
    // TODO:인덱스 처리 필요(풀스캔 위험)
    boolean existsByOrderId(String orderId);
}
