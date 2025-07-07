package life.mosu.mosuserver.infra.storage;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import life.mosu.mosuserver.infra.storage.application.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FileUploadHelper {

    private final S3Service s3Service;

    public void updateTag(String s3Key) {
        s3Service.updateFileTagToActive(s3Key);
    }

    public <R, E> void saveAttachments(
            List<R> requests,
            Long parentId,
            JpaRepository<E, Long> repository,
            BiFunction<R, Long, E> toEntityMapper,
            Function<R, String> getKey
    ) {
        if (requests == null || requests.isEmpty()) {
            return;
        }

        for (R req : requests) {
            String s3Key = getKey.apply(req);
            updateTag(s3Key);
            E entity = toEntityMapper.apply(req, parentId); //DTO -> ToEntity
            repository.save(entity);
        }
    }
}
