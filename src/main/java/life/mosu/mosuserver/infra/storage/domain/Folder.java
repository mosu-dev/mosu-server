package life.mosu.mosuserver.infra.storage.domain;

import life.mosu.mosuserver.global.exception.CustomRuntimeException;
import life.mosu.mosuserver.global.exception.ErrorCode;
import lombok.Getter;

import static life.mosu.mosuserver.infra.storage.domain.Visibility.PRIVATE;
import static life.mosu.mosuserver.infra.storage.domain.Visibility.PUBLIC;

@Getter
public enum Folder {
    EVENT("event", PUBLIC),
    FAQ("faq", PUBLIC),
    NOTICE("notice", PUBLIC),

    TEMP("temp", PRIVATE),
    INQUIRY("inquiry", PRIVATE),
    INQUIRY_ANSWER("inquiryAnswer", PRIVATE),
    ADMISSION_TICKET_IMAGE("admissionTicket/images", PRIVATE),
    ADMISSION_TICKET_PDF("admissionTicket/pdfs", PRIVATE);

    private final String path;
    private final Visibility visibility;

    Folder(String path, Visibility visibility) {
        this.path = path;
        this.visibility = visibility;
    }

    public static Folder validate(String folderName) {
        for (Folder folder : Folder.values())
            if (folder.name().equals(folderName)) {
                return folder;
            }
        throw new CustomRuntimeException(ErrorCode.WRONG_FOLDER_TYPE);
    }
}
