package com.ecnu.timeline.controller;

import com.alibaba.fastjson.JSON;
import com.ecnu.timeline.config.FileMultipartResolver;
import com.ecnu.timeline.config.FileStorageConfig;
import com.ecnu.timeline.domain.News;
import com.ecnu.timeline.service.FileStorageServiceImpl;
import com.ecnu.timeline.service.NewsServiceImpl;
import com.ecnu.timeline.vo.Result;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(value = NewsController.class,includeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = FileMultipartResolver.class
))
class NewsControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private FileStorageConfig fileStorageConfig;

    @MockBean
    private NewsServiceImpl newsService;

    @MockBean
    private FileStorageServiceImpl storageService;

    @Test
    void addNews() throws Exception {
        MvcResult mvcResult = mvc.perform(
                multipart("/add")
                        .file("photo", "123".getBytes())
                        .param("publisher", "yuyuko")
                        .param("content", "测试帖内容")
                        .param("title", "测试帖标题")
        ).andReturn();
        Integer code = ((Result) JSON.parseObject(mvcResult.getResponse().getContentAsByteArray(),
                Result.class)).getCode();
        assertEquals(0, (int) code);
    }

    @Test
    void uploadFile() throws Exception {
        String testFileName = "testfile";
        String downloadUri = "downloadUri";

        when(storageService.storeFile(any())).thenReturn(testFileName);
        when(newsService.addOneFile(anyString(), anyString(), anyInt())).thenReturn(new News().setFileDownloadUri(downloadUri));
        MvcResult mvcResult = mvc.perform(
                multipart("/addFile")
                        .file("file", "123".getBytes())
                        .param("newsId", "1")
        ).andReturn();
        Result result = (Result) JSON.parseObject(mvcResult.getResponse().getContentAsByteArray(),
                Result.class);
        Integer code = result.getCode();
        String fileDownloadUri = (String) ((Map) result.getData()).get("fileDownloadUri");
        assertEquals(0, (int) code);
        assertEquals(downloadUri, fileDownloadUri);
    }

    @Test
    void getLatestNews() throws Exception {
        MvcResult mvcResult = mvc.perform(
                get("/latestNews")
                        .param("time", "2012-11-11 11:11:11")
        ).andReturn();
        Result result = (Result) JSON.parseObject(mvcResult.getResponse().getContentAsByteArray(),
                Result.class);
        Integer code = result.getCode();
        assertEquals(0, (int) code);

    }

    @Test
    void getRecentNews() throws Exception {
        MvcResult mvcResult = mvc.perform(
                get("/recentNews")
                        .param("pageOffset", "0")
        ).andReturn();
        Result result = (Result) JSON.parseObject(mvcResult.getResponse().getContentAsByteArray(),
                Result.class);
        Integer code = result.getCode();
        assertEquals(0, (int) code);
    }

    @Test
    void downloadFile() throws Exception {
        String testFileName = "testfile";
        byte[] testFileBytes = "123".getBytes();
        when(storageService.loadFileAsResource(testFileName)).thenReturn(new ByteArrayResource(
                testFileBytes));
        MvcResult mvcResult = mvc.perform(
                get("/downloadFile/" + testFileName)
        ).andReturn();
        assertArrayEquals(testFileBytes, mvcResult.getResponse().getContentAsByteArray());
    }
}
