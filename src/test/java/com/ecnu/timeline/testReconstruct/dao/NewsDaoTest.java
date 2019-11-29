package com.ecnu.timeline.testReconstruct.dao;

import com.ecnu.timeline.dao.NewsDao;
import com.ecnu.timeline.domain.News;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
/**
 * @author onion
 * @date 2019/11/29 -10:56 上午
 */

public class NewsDaoTest {
    @Autowired
    private NewsDao newsDao;

    @Test
    public void testFindTimeAfter(){
        List<News> newsList = newsDao.findAllByPublishTimeAfter(LocalDateTime.now().minusMonths(1));
        assertTrue(newsList.size() > 0);
    }

    @Test
    public void testNewsPage(){
        List<News> page = newsDao.findNewsPage(0, 5);
        assertTrue(page.size() > 0);
    }

    @Test
    public void testFindAll(){
        Page<News> all = newsDao.findAll(PageRequest.of(0, 5));
        assertTrue(all.getTotalElements() > 0);
    }
}
