package life.mosu.mosuserver.infra.storage.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@MappedSuperclass
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class File {

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String s3Key;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Visibility visibility;

    protected File(String fileName, String s3Key, Visibility visibility) {
        this.fileName = fileName;
        this.s3Key = s3Key;
        this.visibility = visibility;
    }

    public boolean isPublic() {
        return this.visibility == Visibility.PUBLIC;
    }
}