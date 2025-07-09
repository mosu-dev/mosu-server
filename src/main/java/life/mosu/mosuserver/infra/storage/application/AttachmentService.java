package life.mosu.mosuserver.infra.storage.application;

import java.util.List;

public interface AttachmentService<E, R> {

    void createAttachment(List<R> fileRequests, E entity);

    void deleteAttachment(E entity);
}
