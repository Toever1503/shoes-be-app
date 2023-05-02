package com.photoism.cms.domain.file;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.photoism.cms.common.config.AwsS3Config;
import com.photoism.cms.common.exception.ObjectNotFoundException;
import com.photoism.cms.common.model.FileDivision;
import com.photoism.cms.domain.file.dto.FileListResDto;
import com.photoism.cms.domain.file.dto.FileResDto;
import com.photoism.cms.domain.file.entity.FileEntity;
import com.photoism.cms.domain.file.repository.FileEntityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class AwsS3Service {
    private final AmazonS3Client amazonS3Client;
    private final AwsS3Config awsS3Config;
    private final FileEntityRepository fileEntityRepository;

    @Transactional
    public FileListResDto upload(FileDivision prefix, List<MultipartFile> fileList) {
        List<FileEntity> fileEntities = new ArrayList<>();

        try {
            for (MultipartFile tmpFile : fileList) {
                String uid = UUID.randomUUID().toString();
                String folderName = prefix.name();
                String tmpFileName = tmpFile.getOriginalFilename();
                String fileName = prefix.name() + "_" + uid + "_" + tmpFileName;
                String filePath = folderName + "/" + fileName;
                ObjectMetadata objectMetadata = new ObjectMetadata();
                objectMetadata.setContentLength(tmpFile.getSize());
                objectMetadata.setContentType(tmpFile.getContentType());

                try(InputStream inputStream = tmpFile.getInputStream()) {
                    amazonS3Client.putObject(new PutObjectRequest(awsS3Config.getBucket(), filePath, inputStream, objectMetadata));
                }catch (Exception e) {
                    log.error("Unexpected upload file: [{}]", e.getMessage());
                }
                FileEntity file = FileEntity.builder()
                        .name(tmpFileName)
                        .path(amazonS3Client.getUrl(awsS3Config.getBucket(), filePath).toString())
                        .alterName(fileName)
                        .del(false)
                        .division(prefix.name())
                        .build();

                fileEntities.add(file);
            }
        } catch (Exception e) {
            log.error("Unexpected upload file: [{}]", e.getMessage());
        }
        return new FileListResDto(fileEntityRepository.saveAll(fileEntities));
    }

    @Transactional
    public ResponseEntity<byte[]> download(Long id) throws IOException {
        FileEntity fileEntity = fileEntityRepository.findById(id).orElseThrow(()->new ObjectNotFoundException("file"));
        FileResDto file = new FileResDto(fileEntity);
        String fileAlterName = file.getAlterName();
        String prefix = fileEntity.getDivision();

        S3Object s3Object = amazonS3Client.getObject(awsS3Config.getBucket() + "/" + prefix, fileAlterName);
        S3ObjectInputStream objectInputStream = s3Object.getObjectContent();
        byte[] bytes = IOUtils.toByteArray(objectInputStream);
        String fileName = URLEncoder.encode(file.getName(), StandardCharsets.UTF_8).replaceAll("\\+", "%20");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        httpHeaders.setContentLength(bytes.length);
        httpHeaders.setContentDispositionFormData("attachment", fileName);

        return new ResponseEntity<>(bytes, httpHeaders, HttpStatus.OK);
    }
}
