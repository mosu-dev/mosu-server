package life.mosu.mosuserver.domain.inquiryAnswer;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InquiryAnswerJpaRepository extends JpaRepository<InquiryAnswerJpaEntity, Long> {

    Optional<InquiryAnswerJpaEntity> findByInquiryId(Long id);
}
