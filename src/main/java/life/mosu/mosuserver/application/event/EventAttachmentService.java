package life.mosu.mosuserver.application.event;

import java.util.List;
import life.mosu.mosuserver.domain.event.EventAttachmentRepository;
import life.mosu.mosuserver.domain.event.EventJpaEntity;
import life.mosu.mosuserver.global.util.FileRequest;
import life.mosu.mosuserver.infra.property.S3Properties;
import life.mosu.mosuserver.infra.storage.FileUploadHelper;
import life.mosu.mosuserver.infra.storage.application.AttachmentService;
import life.mosu.mosuserver.infra.storage.application.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventAttachmentService implements AttachmentService<EventJpaEntity, FileRequest> {

    private final EventAttachmentRepository eventAttachmentRepository;
    private final FileUploadHelper fileUploadHelper;
    private final S3Service s3Service;
    private final S3Properties s3Properties;


    @Override
    public void createAttachment(List<FileRequest> request, EventJpaEntity eventEntity) {
        if (request == null || request.isEmpty()) {
            return;
        }
        fileUploadHelper.saveAttachments(
                request,
                eventEntity.getId(),
                eventAttachmentRepository,
                (fileRequest, eventId) -> fileRequest.toEventAttachmentEntity(
                        fileRequest.fileName(),
                        fileRequest.s3Key(),
                        eventEntity.getId()
                ),
                FileRequest::s3Key
        );
    }

    @Override
    public void deleteAttachment(EventJpaEntity entity) {
        if (eventAttachmentRepository.findByEventId(entity.getId()).isPresent()) {
            eventAttachmentRepository.deleteByEventId(entity.getId());
        }
    }

}
