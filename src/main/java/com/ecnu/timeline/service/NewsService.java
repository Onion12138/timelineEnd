package com.ecnu.timeline.service;

import com.ecnu.timeline.domain.News;
import com.ecnu.timeline.domain.NewsRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author onion
 * @date 2019/11/29 -9:19 上午
 */
public interface NewsService {
    News addNews(NewsRequest newsRequest);
    News addOneFile(String fileDownloadUri,String fileName,Integer newsId);
    List<News> findLatestNews(String time);
    Page<News> findAll(Pageable pageable);
    List<News> findRecentNews(Integer offset,Integer size);
}
