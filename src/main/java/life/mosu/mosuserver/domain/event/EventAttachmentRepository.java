package life.mosu.mosuserver.domain.event;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventAttachmentRepository extends JpaRepository<EventAttachmentJpaEntity, Long> {

    void deleteByEventId(Long eventId);

    Optional<EventAttachmentJpaEntity> findByEventId(Long eventId);
}

