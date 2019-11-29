package com.ecnu.timeline.controller;

import com.ecnu.timeline.domain.News;
import com.ecnu.timeline.domain.NewsRequest;
import com.ecnu.timeline.service.NewsServiceImpl;
import com.ecnu.timeline.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author onion
 * @date 2019/11/1 -7:48 下午
 */
@RestController
public class NewsController {
    @Autowired
    private NewsServiceImpl newsService;

    @PostMapping("/add")
    public Result addNews(@Valid @RequestBody NewsRequest newsRequest, BindingResult result){
        if (result.hasErrors()){
            StringBuilder sb = new StringBuilder();
            result.getAllErrors().forEach(e->{
                FieldError error = (FieldError) e;
                String field = error.getField();
                String message = error.getDefaultMessage();
                sb.append(field).append(":").append(message).append('\n');
            });
            throw new RuntimeException(sb.toString());
        }
        return new Result(newsService.addNews(newsRequest), 0, "发布成功");
    }


    @GetMapping("/latestNews")
    public Result getLatestNews(@RequestParam String time){
        List<News> latestNews = newsService.findLatestNews(time);
        return new Result(latestNews, 0, "查找成功");
    }
    @GetMapping("/recentNews")
    public Result getRecentNews(@RequestParam(value = "pageOffset",defaultValue = "0")Integer pageOffset){
        List<News> pages=newsService.findRecentNews(pageOffset,5);
        return new Result(pages,0,"查找成功");
    }

}
