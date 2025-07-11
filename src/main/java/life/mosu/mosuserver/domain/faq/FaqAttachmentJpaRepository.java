package life.mosu.mosuserver.domain.faq;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FaqAttachmentJpaRepository extends JpaRepository<FaqAttachmentJpaEntity, Long> {

    List<FaqAttachmentJpaEntity> findAllByFaqId(Long faqId);
}