package life.mosu.mosuserver.infra.storage.presentation;

import jakarta.validation.constraints.NotNull;
import life.mosu.mosuserver.global.exception.CustomRuntimeException;
import life.mosu.mosuserver.global.exception.ErrorCode;
import life.mosu.mosuserver.global.util.ApiResponseWrapper;
import life.mosu.mosuserver.infra.storage.application.S3Service;
import life.mosu.mosuserver.infra.storage.domain.Folder;
import life.mosu.mosuserver.infra.storage.presentation.dto.FileUploadResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/s3")
public class S3Controller {
    private final S3Service s3Service;

    @PostMapping
    public ApiResponseWrapper<FileUploadResponse> uploadFile(
        @RequestParam("file") MultipartFile file,
        @RequestParam(defaultValue = "temp") String folderName
        ) {

        if (file.isEmpty()) {
            throw new CustomRuntimeException(ErrorCode.FILE_UPLOAD_FAILED, "업로드할 파일이 비어 있습니다.");
        }

        Folder folder = Folder.validate(folderName);

        FileUploadResponse fileResponse = s3Service.uploadFile(file, folder);
        return ApiResponseWrapper.success(HttpStatus.CREATED, "파일 업로드 성공", fileResponse);
    }
}
