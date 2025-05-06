package com.easyroutine.infrastructure.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.easyroutine.global.exception.BusinessException;
import com.easyroutine.global.response.ResultType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3ServiceImpl implements S3Service{

    private final AmazonS3 amazonS3;

    @Value("${cloud.s3.bucket}")
    private String bucketName;

    @Override
    public String uploadFile(MultipartFile multipartFile, String directoryPath) {
        String fileName = getFileName(multipartFile, directoryPath);
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(multipartFile.getSize());
        objectMetadata.setContentType(multipartFile.getContentType());

        uploadFile2S3(multipartFile, fileName, objectMetadata);

        return amazonS3.getUrl(bucketName, fileName).toString();
    }

    @Override
    public boolean deleteFile(String url) {
        try {
            String fileKey = getFileKey(url);
            amazonS3.deleteObject(bucketName, fileKey);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void uploadFile2S3(MultipartFile multipartFile, String fileName, ObjectMetadata objectMetadata) {
        try{
            amazonS3.putObject(
                    new PutObjectRequest(bucketName, fileName, multipartFile.getInputStream(), objectMetadata)
                            .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (Exception e) {
            throw new BusinessException(ResultType.S3_UPLOAD_FAIL, e.getMessage());
        }
    }

    private String getFileName(MultipartFile multipartFile, String url) {
        return url + "/" + UUID.randomUUID() + "_" + multipartFile.getOriginalFilename();
    }

    private String getFileKey(String url) {
        String[] fileKeySplit = url.split(".com/");
        return fileKeySplit[1];
    }
}
