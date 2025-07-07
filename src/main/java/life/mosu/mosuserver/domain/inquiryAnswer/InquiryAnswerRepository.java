package life.mosu.mosuserver.domain.inquiryAnswer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InquiryAnswerRepository extends JpaRepository<InquiryAnswerJpaEntity, Long> {

    InquiryAnswerJpaEntity findByInquiryId(Long id);
}
