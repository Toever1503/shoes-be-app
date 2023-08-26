package com.shoescms.common.service;

import com.google.cloud.storage.Bucket;
import com.google.firebase.cloud.StorageClient;
import com.shoescms.common.model.FileEntity;
import com.shoescms.common.model.repositories.FileRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
public class FileUploadService {

    private final Bucket DEFAULT_BUCKET;
    private final String STORAGE_URL = "https://firebasestorage.googleapis.com/v0/b/springboot-analysis.appspot.com/o/";
    private final String DEFAULT_PATH = "tmp/";
    private final FileRepository fileRepository;

    public FileUploadService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
        this.DEFAULT_BUCKET = StorageClient.getInstance().bucket();
    }

    private String generatePath(String path, String fileName) {
        if (ObjectUtils.isEmpty(path))
            return DEFAULT_PATH + fileName;
        else
            return path + "/" + fileName;
    }

    public FileEntity uploadFile(String path, String fileName, MultipartFile file) throws IOException {
        String filePath = generatePath(path, fileName);
        DEFAULT_BUCKET.create(filePath, file.getInputStream(), file.getContentType());
        return fileRepository.saveAndFlush(new FileEntity(STORAGE_URL + filePath));
    }
}
