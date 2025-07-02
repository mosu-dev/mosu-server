package life.mosu.mosuserver.infra.storage.application;

import life.mosu.mosuserver.infra.storage.domain.File;
import life.mosu.mosuserver.infra.storage.domain.Folder;
import life.mosu.mosuserver.infra.storage.presentation.dto.FileUploadResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3Service {

    private final S3Client s3Client;
    private final S3Presigner s3Presigner;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    @Value("${aws.s3.presigned-url-expiration-minutes}")
    private int preSignedUrlExpirationMinutes;


    public FileUploadResponse uploadFile(MultipartFile file, Folder folder) {
        String sanitizedName = sanitizeFileName(file.getOriginalFilename());
        String s3Key = folder.getPath() + "/" + UUID.randomUUID() + "_" + sanitizedName;

        try {
            s3Client.putObject(
                    PutObjectRequest.builder()
                            .bucket(bucketName)
                            .key(s3Key)
                            .contentType(file.getContentType())
                            .build(),
                    RequestBody.fromInputStream(file.getInputStream(), file.getSize())
            );
        } catch (IOException e) {
            throw new RuntimeException("파일 스트림을 읽는 중 오류 발생", e);
        } catch (S3Exception e) {
            throw new RuntimeException("S3 업로드 실패", e);
        }

        return FileUploadResponse.of(file.getOriginalFilename(), s3Key);
    }

    public void deleteFile(File file) {
        try {
            s3Client.deleteObject(DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(file.getS3Key())
                    .build());
        } catch (S3Exception e) {
            throw new RuntimeException("S3 파일 삭제 실패", e);
        }
    }

    public void moveFile(String sourceKey, Folder folder){
        final String destinationKey = folder.getPath() + "/" + sourceKey.substring("temp/".length());

        CopyObjectRequest copyRequest = CopyObjectRequest.builder()
                .sourceBucket(bucketName)
                .sourceKey(sourceKey)
                .destinationBucket(bucketName)
                .destinationKey(destinationKey)
                .build();

        try {
            s3Client.copyObject(copyRequest);
            log.info("파일 이동 성공: {} -> {}", shortenKey(sourceKey), shortenKey(destinationKey));
        } catch (S3Exception e) {
            throw new RuntimeException("S3 파일 이동 실패", e);
        }
    }

    public String getUrl(File file) {
        return file.isPublic()
                ? getPublicUrl(file.getS3Key())
                : getPreSignedUrl(file.getS3Key(), Duration.ofMinutes(preSignedUrlExpirationMinutes));
    }

    public String getPublicUrl(String s3Key) {
        return String.format("https://%s.s3.amazonaws.com/%s", bucketName, s3Key);
    }

    public String getPreSignedUrl(String s3Key, Duration expireDuration) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(s3Key)
                .build();

        GetObjectPresignRequest preSignRequest = GetObjectPresignRequest.builder()
                .getObjectRequest(getObjectRequest)
                .signatureDuration(expireDuration)
                .build();

        return s3Presigner.presignGetObject(preSignRequest).url().toString();
    }

    private String sanitizeFileName(String originalFilename) {
        try {
            return URLEncoder.encode(originalFilename, StandardCharsets.UTF_8)
                    .replaceAll("\\+", "%20");
        } catch (Exception e) {
            throw new RuntimeException("파일 이름 인코딩 실패", e);
        }
    }

    private String shortenKey(String key) {
        if (key.length() <= 40) {
            return key;
        }
        return key.substring(0, 10) + "..." + key.substring(key.length() - 20);
    }
}
