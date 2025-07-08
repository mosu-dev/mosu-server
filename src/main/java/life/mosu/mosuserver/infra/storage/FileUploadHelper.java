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

    /**
     * Marks the file identified by the given S3 key as active in S3 storage.
     *
     * @param s3Key the S3 key of the file to update
     */
    public void updateTag(String s3Key) {
        s3Service.updateFileTagToActive(s3Key);
    }

    /**
     * Processes a list of request objects by marking their associated S3 files as active and saving corresponding entities to the database.
     *
     * For each request, extracts the S3 key, updates the file's tag to active in S3, maps the request and parent ID to an entity, and persists the entity using the provided repository.
     *
     * No action is taken if the request list is null or empty.
     *
     * @param requests       the list of request objects to process
     * @param parentId       the identifier to associate with each entity
     * @param repository     the JPA repository used to save entities
     * @param toEntityMapper a function that maps a request and parent ID to an entity
     * @param getKey         a function that extracts the S3 key from a request
     */
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
