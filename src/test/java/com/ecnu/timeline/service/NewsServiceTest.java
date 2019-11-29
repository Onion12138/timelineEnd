package com.ecnu.timeline.service;

import com.ecnu.timeline.dao.NewsDao;
import com.ecnu.timeline.domain.News;
import com.ecnu.timeline.domain.NewsRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest(includeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = NewsServiceImpl.class
))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class NewsServiceTest {
    @Autowired
    private NewsServiceImpl newsService;

    @Autowired
    private NewsDao newsDao;

    Integer insertTestNews() {
        News news = new News()
                .setPublisher("yuyuko")
                .setTitle("测试贴标题")
                .setContent("测试贴内容")
                .setPublishTime(LocalDateTime.now());
        return newsDao.save(news).getId();
    }

    @Test
    void addNews() {
        NewsRequest newsRequest = new NewsRequest("yuyuko", "测试贴内容", "测试贴标题",
                null);
        assertNotNull(newsService.addNews(newsRequest).getId());
    }

/*    @Test
    void addNewsWithPhoto() {
        MockMultipartFile photo = new MockMultipartFile("帖子图片", "123".getBytes());
        NewsRequest newsRequest = new NewsRequest("yuyuko", "测试贴内容", "测试贴标题",
                photo);
        assertNotNull(newsService.addNews(newsRequest).getId());
        assertNotNull(newsService.addNews(newsRequest).getShowUrl());
    }*/

    @Test
    void addOneFile() {
        Integer newsId = insertTestNews();
        News news = newsService.addOneFile("测试帖附件下载url", "测试帖附件", newsId);
        assertNotNull(news.getFileDownloadUri());
    }

    @Test
    void findLatestNews() throws InterruptedException {
        String now = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).toString().replace('T', ' ');
        TimeUnit.SECONDS.sleep(1);
        insertTestNews();
        assertEquals(1, newsService.findLatestNews(now).size());
        insertTestNews();
        assertEquals(2, newsService.findLatestNews(now).size());
    }

    @Test
    void findRecentNews() throws InterruptedException {
        Integer news1Id = insertTestNews();
        TimeUnit.SECONDS.sleep(1);
        Integer news2Id = insertTestNews();

        List<News> recentNews1 = newsService.findRecentNews(0, 1);
        assertEquals(1, recentNews1.size());
        assertEquals(news2Id, recentNews1.get(0).getId());

        List<News> recentNews2 = newsService.findRecentNews(0, 2);
        assertEquals(2, recentNews2.size());
        assertEquals(news2Id, recentNews2.get(0).getId());
        assertEquals(news1Id, recentNews2.get(1).getId());
    }
}
