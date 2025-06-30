package life.mosu.mosuserver.domain.faq;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FaqAttachmentRepository extends JpaRepository<FaqAttachmentJpaEntity, Long> {
    List<FaqAttachmentJpaEntity> findAllByFaqId(Long faqId);
}
