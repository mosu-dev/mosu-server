package life.mosu.mosuserver.domain.inquiry;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InquiryJpaRepository extends JpaRepository<InquiryJpaEntity, Long>,
        InquiryQueryRepository {

}
