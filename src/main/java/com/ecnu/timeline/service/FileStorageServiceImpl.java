package com.ecnu.timeline.service;


import com.ecnu.timeline.config.FileStorageConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
@Slf4j
public class FileStorageServiceImpl implements FileStorageService {

    private final Path fileStorageLocation;

    @Autowired
    public FileStorageServiceImpl(FileStorageConfig fileStorageConfig) {
        this.fileStorageLocation = Paths.get(fileStorageConfig.getUploadDir())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception e) {
            log.error("无法创建文件路径");
            throw new RuntimeException("无法新建文件夹");
        }
    }

    public String storeFile(MultipartFile file) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                log.error("上传文件路径非法");
                throw new RuntimeException("文件名非法");
            }

            // Copy file to the target location (Replacing existing file with the same name)
            // store the newest one
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException e) {
            log.error("上传文件失败，建议重新尝试,fileName ={}",fileName);
            throw new RuntimeException("图片存储失败，请重新尝试");
        }
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                log.error("找不到文件, fileName = {}",fileName);
                throw new Exception();
            }
        } catch (Exception e) {
            throw new RuntimeException("找不到该图片，可能已经被删除");
        }
    }

}
