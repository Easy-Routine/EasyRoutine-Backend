package com.easyroutine.infrastructure.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;

import java.net.URL;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class S3ServiceImplTest {

	@Mock
	private AmazonS3 amazonS3;

	@InjectMocks
	private S3ServiceImpl s3Service;

	@DisplayName("S3에 파일 업로드 성공시에 S3 URL을 반환한다.")
	@Test
	void uploadFileToS3() throws Exception {

		// given
		MockMultipartFile mockFile = new MockMultipartFile("file", "test.jpg", "text/jpg", "test content".getBytes());

		String url = "uploads";
		String fileNamePattern = url + "/" + UUID.randomUUID() + "_" + mockFile.getOriginalFilename();

		given(amazonS3.getUrl(any(), any(String.class)))
			.willReturn(new URL("https://test-bucket.s3.amazonaws.com/" + fileNamePattern));

		// when
		String result = s3Service.uploadFile(mockFile, url);

		// then
		verify(amazonS3).putObject(any(PutObjectRequest.class));
		assertTrue(result.contains("https://test-bucket.s3.amazonaws.com/" + fileNamePattern));
	}

	@DisplayName("deleteFile_성공시_true_리턴")
	@Test
	void deleteFileFromS3() {

		// given
		String url = "https://test-bucket.s3.amazonaws.com/images/test.jpg";

		// when
		boolean result = s3Service.deleteFile(url);

		// then
		assertTrue(result);
	}

}