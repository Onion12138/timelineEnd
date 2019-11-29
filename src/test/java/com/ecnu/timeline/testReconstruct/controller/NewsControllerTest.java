package com.ecnu.timeline.testReconstruct.controller;

import com.alibaba.fastjson.JSON;
import com.ecnu.timeline.controller.NewsController;
import com.ecnu.timeline.domain.News;
import com.ecnu.timeline.service.FileStorageService;
import com.ecnu.timeline.service.NewsService;
import com.ecnu.timeline.vo.Result;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;

/**
 * @author onion
 * @date 2019/11/29 -9:28 上午
 */
@WebMvcTest(value = NewsController.class)
public class NewsControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private NewsService newsService;
    @MockBean
    private FileStorageService storageService;
    @Test
    public void shouldGetLatestNews() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/latestNews").param("time", "2019-11-11 11:11:11")).andReturn();
        Result result = JSON.parseObject(mvcResult.getResponse().getContentAsByteArray(), Result.class);
        assertEquals(0, (int)result.getCode());
        assertTrue(result.getData() instanceof List);
        assertEquals("查找成功", result.getMessage());
        verify(newsService, times(1)).findLatestNews("2019-11-11 11:11:11");
    }

    @Test
    public void shouldGetRecentNews() throws Exception{
        MvcResult mvcResult = mockMvc.perform(get("/recentNews").param("pageOffset", "0")).andReturn();
        Result result = JSON.parseObject(mvcResult.getResponse().getContentAsByteArray(), Result.class);
        assertEquals(0, (int)result.getCode());
        assertTrue(result.getData() instanceof List);
        assertEquals("查找成功", result.getMessage());
        verify(newsService, times(1)).findRecentNews(0, 5);
    }

    @Test
    public void shouldAddNews() throws Exception{
        MvcResult mvcResult = mockMvc.perform(multipart("/add")
                .file("photo", "123".getBytes())
                .param("publisher", "onion")
                .param("content", "内容")
                .param("title", "标题")).andReturn();
        Result result = JSON.parseObject(mvcResult.getResponse().getContentAsByteArray(), Result.class);
        assertEquals(0, (int)result.getCode());
        assertTrue(result.getData() instanceof News);
        assertEquals("发布成功", result.getMessage());
    }

    @Test
    public void shouldNotAddNews() throws Exception{
        MvcResult mvcResult = mockMvc.perform(multipart("/add")
                .file("photo", "123".getBytes())
                .param("publisher", "onion")
                .param("content", "")
                .param("title", "标题")).andReturn();
        Result result = JSON.parseObject(mvcResult.getResponse().getContentAsByteArray(), Result.class);
        assertEquals(-1, (int) result.getCode());
    }

    @Test
    void uploadFile() throws Exception {
        String testFileName = "testfile";
        String downloadUri = "downloadUri";
        when(storageService.storeFile(any())).thenReturn(testFileName);
        when(newsService.addOneFile(anyString(), anyString(), anyInt())).thenReturn(new News().setFileDownloadUri(downloadUri));
        MvcResult mvcResult = mockMvc.perform(
                multipart("/addFile")
                        .file("file", "123".getBytes())
                        .param("newsId", "1")
        ).andReturn();
        Result result = JSON.parseObject(mvcResult.getResponse().getContentAsByteArray(),
                Result.class);
        Integer code = result.getCode();
        String fileDownloadUri = ((News) result.getData()).getFileDownloadUri();
        assertEquals(0, (int) code);
        assertEquals(downloadUri, fileDownloadUri);
    }

}
