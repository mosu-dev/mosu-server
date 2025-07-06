package life.mosu.mosuserver.infra.storage.application;

import java.util.List;

public interface AttachmentService<T, R>{
    void createAttachment(List<R> fileRequests, T entity);
    void deleteAttachment(T entity);
}
