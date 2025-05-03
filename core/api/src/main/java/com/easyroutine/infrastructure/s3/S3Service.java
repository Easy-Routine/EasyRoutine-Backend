package com.easyroutine.infrastructure.s3;

import org.springframework.web.multipart.MultipartFile;

public interface S3Service {

    String uploadFile(MultipartFile multipartFile, String url);

    boolean deleteFile(String url);
}
