package life.mosu.mosuserver.infra.storage.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InquiryFile extends File {

    @Builder
    public InquiryFile(String fileName, String s3Key, Visibility visibility) {
        super(fileName, s3Key, visibility);
    }
}
