package life.mosu.mosuserver.presentation.notice.dto;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import life.mosu.mosuserver.global.util.FileRequest;

public record NoticeUpdateRequest(
        @NotNull String title,
        @NotNull String content,
        Long userId,
        List<FileRequest> attachments

) {

}