package com.ecnu.timeline.service;

import com.ecnu.timeline.dao.NewsDao;
import com.ecnu.timeline.domain.News;
import com.ecnu.timeline.domain.NewsRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Formatter;
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
//        if (result.hasErrors()){
//            StringBuilder sb = new StringBuilder();
//            result.getAllErrors().forEach(e->{
//                FieldError error = (FieldError) e;
//                String field = error.getField();
//                String message = error.getDefaultMessage();
//                sb.append(field).append(":").append(message).append('\n');
//            });
//            throw new RuntimeException(sb.toString());
//        }
        News news = new News();
        BeanUtils.copyProperties(newsRequest, news);
        news.setPublishTime(LocalDateTime.now());
        if (newsRequest.getPhoto()!=null){
            System.out.println("???");
            try {
                news.setShowUrl(makePhotoUrl(newsRequest.getPhoto()));
            } catch (IOException e) {
                throw new RuntimeException("fos出错");
            }
        }

        System.out.println(news.getContent());
        return newsDao.save(news);
    }

    public News addOneFile(String fileDownloadUri,String fileName,Integer newsId){
        News news = findOne(newsId);
        news.setFileDownloadUri(fileDownloadUri);
        return newsDao.save(news);
    }

    public List<News> findLatestNews(String time){
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(time,df);
        return newsDao.findAllByPublishTimeAfter(localDateTime);
    }

    private News findOne(Integer newsId){
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

    private String makePhotoUrl(MultipartFile photo) throws IOException {
        FileOutputStream fos = null;
        String fn = StringUtils.cleanPath(photo.getOriginalFilename());
        try {
            fos = new FileOutputStream("/Users/pengfeng/Desktop/大三上/软件测试/timelineEnd/img/" +fn);
            fos.write(photo.getBytes());
        } catch (Exception e) {
            throw new RuntimeException("路径名非法");
        }finally {
            if (fos!=null) {
                fos.close();
            }
        }
        try {
//                assert fos != null;
            fos.close();
        } catch (IOException  e) {
            e.printStackTrace();
            log.warn("非正常关闭fos");
        }
        // 使用 nginx来做
        return "http://xxx.xxx.xxx.xxx/img/"+fn;
    }
}
