package com.ecnu.timeline.service;

import com.ecnu.timeline.dao.NewsDao;
import com.ecnu.timeline.domain.News;
import com.ecnu.timeline.domain.NewsRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author onion
 * @date 2019/11/2 -6:13 下午
 */
@Service
public class NewsService {
    @Autowired
    private NewsDao newsDao;

    public void addNews(NewsRequest newsRequest) {
        News news = new News();
        news.setPublishTime(new Date());
        news.setPublisher(newsRequest.getPublisher());
        news.setContent(newsRequest.getContent());
        news.setTitle(newsRequest.getTitle());
        newsDao.save(news);
    }

    public List<News> findLatestNews(String time){
        long timestamp = Long.parseLong(time);
        Date date = new Date(timestamp);
        return newsDao.findAllByPublishTimeAfter(date);
    }

    public Page<News> findAll(Pageable pageable) {
        return newsDao.findAll(pageable);
    }

    public List<News> findRecentNews(Integer offset,Integer size){
        return newsDao.findNewsPage(offset,size);
    }
}
