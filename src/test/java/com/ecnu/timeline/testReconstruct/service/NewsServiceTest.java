package com.ecnu.timeline.testReconstruct.service;

import com.ecnu.timeline.dao.NewsDao;
import com.ecnu.timeline.domain.News;
import com.ecnu.timeline.domain.NewsRequest;
import com.ecnu.timeline.service.NewsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * @author onion
 * @date 2019/11/29 -10:26 上午
 */
@SpringBootTest
public class NewsServiceTest {
    @Autowired
    private NewsService newsService;

    @Autowired
    private NewsDao newsDao;

    private News news;

    private NewsRequest newsRequest;

    @BeforeEach
    public void init(){
        news = new News();
        news.setContent("内容");
        news.setId(100);
        news.setTitle("标题");
        newsRequest = new NewsRequest();
        newsRequest.setPublisher("onion");
        newsRequest.setContent("内容");
        newsRequest.setTitle("标题");
    }
    @Test
    void testAddNews(){
        when(newsService.addNews(newsRequest)).thenReturn(news);
        newsService.addNews(newsRequest);
        verify(newsDao, times(1)).save(news);
    }
    @Test
    void testAddOneFile(){
        when(newsService.addOneFile(anyString(), anyString(), anyInt())).thenReturn(news);
        newsService.addOneFile("http://baidu.com", "testFile", 100);
        verify(newsDao, times(1)).save(news);
    }
    @Test
    void testFindLatestNews(){
        when(newsService.findLatestNews(any())).thenReturn(Arrays.asList(news));
        newsService.findLatestNews("2019-11-11 11:11:11");
        verify(newsDao, times(1)).findAllByPublishTimeAfter(any());
    }
    @Test
    void testFindAll(){
        Pageable page = PageRequest.of(1, 5);
        when(newsService.findAll(page)).thenReturn(any());
        newsService.findAll(page);
        verify(newsDao, times(1)).findAll(page);
    }
    @Test
    void findRecentNews(){
        when(newsService.findRecentNews(anyInt(), anyInt())).thenReturn(Arrays.asList(news));
        newsService.findRecentNews(0, 5);
        verify(newsDao, times(1)).findNewsPage(0, 5);
    }
}
