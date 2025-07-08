package life.mosu.mosuserver.infra.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import life.mosu.mosuserver.infra.storage.application.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
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
        List<E> entitiesToSave = new ArrayList<>(requests.size());
        R req = null;

        try {
            for (R r : requests) {
                req = r;
                String s3Key = getKey.apply(req);
                updateTag(s3Key);
                E entity = toEntityMapper.apply(req, parentId); //DTO -> ToEntity
                entitiesToSave.add(entity);
            }
        } catch (Exception e) {
            log.error("saving attachments 에러 발생{}: {}", req, e.getMessage());
        }
        repository.saveAll(entitiesToSave);
    }
}
