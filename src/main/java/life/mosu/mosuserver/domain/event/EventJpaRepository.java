package life.mosu.mosuserver.domain.event;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EventJpaRepository extends JpaRepository<EventJpaEntity, Long> {

    @Query("""
            select new life.mosu.mosuserver.domain.event.EventWithAttachmentProjection(
                e.id,
                e.title,
                e.duration.endDate,
                e.eventLink,
                new life.mosu.mosuserver.domain.event.AttachmentProjection(
                    ea.fileName,
                    ea.s3Key
                )
            )
            from EventJpaEntity e
            left join EventAttachmentJpaEntity ea
            on e.id = ea.eventId
            """)
    List<EventWithAttachmentProjection> findAllWithAttachment();

    @Query("""
             select new life.mosu.mosuserver.domain.event.EventWithAttachmentProjection(
                 e.id,
                 e.title,
                 e.duration.endDate,
                 e.eventLink,
                 new life.mosu.mosuserver.domain.event.AttachmentProjection(
                     ea.fileName,
                     ea.s3Key
                 )
             )
             from EventJpaEntity e
             left join EventAttachmentJpaEntity ea
             on e.id = ea.eventId
             WHERE e.id = :id
            """)
    Optional<EventWithAttachmentProjection> findWithAttachmentById(Long id);
}
