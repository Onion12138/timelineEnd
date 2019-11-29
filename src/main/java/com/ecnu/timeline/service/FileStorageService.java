package com.ecnu.timeline.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author onion
 * @date 2019/11/29 -9:26 上午
 */
public interface FileStorageService {
    String storeFile(MultipartFile file);
    Resource loadFileAsResource(String fileName);
}
