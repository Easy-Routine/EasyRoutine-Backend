package com.easyroutine.api.controller;

import com.easyroutine.infrastructure.s3.S3Service;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "이미지 API", description = "이미지 관련 API")
@RestController
@RequestMapping("/api/v1/images")
public class ImageController {

	private final S3Service s3Service;

	private final String uploadDirectoryPath;

	public ImageController(@Value("${cloud.s3.directory}") String uploadDirectoryPath, S3Service s3Service) {
		this.uploadDirectoryPath = uploadDirectoryPath;
		this.s3Service = s3Service;
	}

	@PostMapping(value = "/upload",
			consumes = { MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public String uploadImageAndGetImageUrl(@RequestPart(value = "image", required = false) MultipartFile image) {
		if (image == null || image.isEmpty()) {
			return null;
		}
		return s3Service.uploadFile(image, uploadDirectoryPath);
	}

}
