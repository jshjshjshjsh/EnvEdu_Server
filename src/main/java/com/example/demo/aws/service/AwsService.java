package com.example.demo.aws.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AwsService {

    private final AmazonS3 amazonS3;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public List<String> saveMultiPartFiles(String fileBaseName, List<MultipartFile> multipartFiles) throws IOException {
        List<String> result = new ArrayList<>();
        for (MultipartFile file : multipartFiles) {

            String savingFileName = fileBaseName + LocalDateTime.now().toString().replace(":","-");

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            metadata.setContentType(file.getContentType());

            amazonS3.putObject(bucket, savingFileName, file.getInputStream(), metadata);
            result.add(amazonS3.getUrl(bucket, savingFileName).toString());
        }
        return result;
    }
}
