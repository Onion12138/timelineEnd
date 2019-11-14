package com.ecnu.timeline.service;

import com.ecnu.timeline.dao.NewsDao;
import com.ecnu.timeline.domain.News;
import com.ecnu.timeline.domain.NewsRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author onion
 * @date 2019/11/2 -6:13 下午
 */
@Service
@Slf4j
public class NewsService {
    @Autowired
    private NewsDao newsDao;

    public News addNews(NewsRequest newsRequest) {
        News news = new News();
        news.setPublishTime(new Date());
        news.setPublisher(newsRequest.getPublisher());
        news.setContent(newsRequest.getContent());
        news.setTitle(newsRequest.getTitle());
        return newsDao.save(news);
    }

    public News addOnePhoto(String fileDownloadUri,String fileName,Integer newsId){
        News news = findOne(newsId);
        news.setFileDownloadUri(fileDownloadUri);
        return newsDao.save(news);
    }

    public List<News> findLatestNews(String time){
        long timestamp = Long.parseLong(time);
        Date date = new Date(timestamp);
        return newsDao.findAllByPublishTimeAfter(date);
    }

    public News findOne(Integer newsId){
        Optional<News> result = newsDao.findById(newsId);
        if (result.isEmpty()){
            log.error("没找到不到news");
            throw new RuntimeException("无此news");
        }
        return result.get();
    }

    public Page<News> findAll(Pageable pageable) {
        return newsDao.findAll(pageable);
    }

    public List<News> findRecentNews(Integer offset,Integer size){
        return newsDao.findNewsPage(offset,size);
    }
}
