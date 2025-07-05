package life.mosu.mosuserver.infra.storage.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import life.mosu.mosuserver.domain.base.BaseTimeEntity;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class FileMoveFailLog extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long faqId;

    private String s3Key;

    private Folder destinationFolder;

    private FileMoveFailLog(Long faqId, String s3Key, Folder destinationFolder) {
        this.s3Key = s3Key;
        this.destinationFolder = destinationFolder;
        this.faqId = faqId;
    }

    public static FileMoveFailLog of(Long faqId, String s3Key, Folder destinationFolder) {
        return new FileMoveFailLog(faqId, s3Key, destinationFolder);
    }


}
