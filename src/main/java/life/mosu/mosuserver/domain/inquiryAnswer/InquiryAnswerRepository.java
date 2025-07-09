package life.mosu.mosuserver.domain.inquiryAnswer;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InquiryAnswerRepository extends JpaRepository<InquiryAnswerJpaEntity, Long> {

    Optional<InquiryAnswerJpaEntity> findByInquiryId(Long id);
}
