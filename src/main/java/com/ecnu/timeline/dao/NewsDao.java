package com.ecnu.timeline.dao;

import com.ecnu.timeline.domain.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * @author onion
 * @date 2019/11/2 -6:14 下午
 */
public interface NewsDao extends JpaRepository<News, Integer> {
    List<News> findAllByPublishTimeAfter(Date date);
    Page<News> findAll(Pageable pageable);

    @Query(value = "SELECT * FROM news ORDER BY timestamp DESC LIMIT ?1,?2" ,nativeQuery = true)
    List<News> findNewsPage(Integer offset,Integer size);
}
