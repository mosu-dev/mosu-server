package life.mosu.mosuserver.infra.storage.presentation.dto;

public record FileUploadResponse(
    String fileName,
    String s3Key
) {
    public static FileUploadResponse of(String fileName, String s3Key) {
        return new FileUploadResponse(fileName, s3Key);
    }
}
