package life.mosu.mosuserver.application.event;

import java.util.List;
import life.mosu.mosuserver.domain.event.EventJpaEntity;
import life.mosu.mosuserver.domain.event.EventJpaRepository;
import life.mosu.mosuserver.domain.event.EventWithAttachmentProjection;
import life.mosu.mosuserver.global.exception.CustomRuntimeException;
import life.mosu.mosuserver.global.exception.ErrorCode;
import life.mosu.mosuserver.infra.storage.application.S3Service;
import life.mosu.mosuserver.presentation.event.dto.EventRequest;
import life.mosu.mosuserver.presentation.event.dto.EventResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventJpaRepository eventJpaRepository;
    private final EventAttachmentService attachmentService;
    private final S3Service s3Service;

    @Transactional
    public void createEvent(EventRequest request) {
        EventJpaEntity eventEntity = eventJpaRepository.save(request.toEntity());
        attachmentService.createAttachment(request.optionalAttachment(), eventEntity);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<EventResponse> getEvents() {
        List<EventWithAttachmentProjection> events = eventJpaRepository.findAllWithAttachment();

        return events.stream()
                .map(event -> {
                    String url = event.attachment().s3Key() != null ? s3Service.getPublicUrl(
                            event.attachment().s3Key()) : null;
                    return EventResponse.of(event, url);
                })
                .toList();
    }


    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public EventResponse getEventDetail(Long eventId) {
        EventWithAttachmentProjection eventEntity = eventJpaRepository.findWithAttachmentById(
                        eventId)
                .orElseThrow(() -> new CustomRuntimeException(ErrorCode.EVENT_NOT_FOUND));

        String eventUrl = eventEntity.attachment().s3Key() != null ? s3Service.getPublicUrl(
                eventEntity.attachment().s3Key()) : null;

        return EventResponse.of(eventEntity, eventUrl);
    }

    @Transactional
    public void update(EventRequest request, Long eventId) {
        EventJpaEntity eventEntity = eventJpaRepository.findById(eventId)
                .orElseThrow(() -> new CustomRuntimeException(ErrorCode.EVENT_NOT_FOUND));

        eventEntity.update(request.title(), request.duration().toDurationJpaVO(),
                request.eventLink());
        eventJpaRepository.save(eventEntity);

        attachmentService.deleteAttachment(eventEntity);
        attachmentService.createAttachment(request.optionalAttachment(), eventEntity);
    }

    @Transactional
    public void deleteEvent(Long eventId) {
        EventJpaEntity eventEntity = eventJpaRepository.findById(eventId)
                .orElseThrow(() -> new CustomRuntimeException(ErrorCode.FILE_NOT_FOUND));

        eventJpaRepository.delete(eventEntity);
        attachmentService.deleteAttachment(eventEntity);
    }

}
