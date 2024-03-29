package com.ecnu.timeline.testReconstruct.service;

import com.ecnu.timeline.config.FileStorageConfig;
import com.ecnu.timeline.service.FileStorageServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.mock.web.MockMultipartFile;
import utils.EmptyTest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author onion
 * @date 2019/11/29 -10:52 上午
 */
@EmptyTest(includeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = FileStorageServiceImpl.class
))
@EnableConfigurationProperties(FileStorageConfig.class)
public class FileStorageServiceTest {
    @Autowired
    private FileStorageServiceImpl fileStorageService;

    @Autowired
    private FileStorageConfig fileStorageConfig;

    @Test
    void storeFile() {
        MockMultipartFile file1 = new MockMultipartFile("testFileName", "testFileName", null,
                "123".getBytes());
        fileStorageService.storeFile(file1);

        MockMultipartFile file2 = new MockMultipartFile("..", "..", null, "123".getBytes());
        assertThrows(RuntimeException.class, () -> fileStorageService.storeFile(file2));
    }

    @AfterEach
    void deleteDirectoryFiles() throws IOException {
        delDirectoryFiles(Paths.get(fileStorageConfig.getUploadDir())
                .toAbsolutePath().normalize(), true);
    }

    void createFiles(String fileName) throws IOException {
        Files.createFile(Paths.get(fileStorageConfig.getUploadDir())
                .toAbsolutePath().normalize().resolve(fileName));
    }

    void delDirectoryFiles(Path path, boolean first) throws IOException {
        if (Files.notExists(path))
            return;
        if (Files.isDirectory(path)) {
            Files.list(path).forEach(path1 -> {
                try {
                    delDirectoryFiles(path1, false);
                } catch (IOException ignore) {
                }
            });
        }
        if (!first)
            Files.delete(path);
    }

    @Test
    void loadFileAsResource() throws IOException {
        createFiles("123");
        assertNotNull(fileStorageService.loadFileAsResource("123"));
        assertThrows(RuntimeException.class, () -> fileStorageService.loadFileAsResource("456"));
    }
}

