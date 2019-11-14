package com.ecnu.timeline.controller;

import com.ecnu.timeline.domain.News;
import com.ecnu.timeline.domain.NewsRequest;
import com.ecnu.timeline.service.FileStorageService;
import com.ecnu.timeline.service.NewsService;
import com.ecnu.timeline.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.IOException;
import java.util.List;

/**
 * @author onion
 * @date 2019/11/1 -7:48 下午
 */
@RestController
@Slf4j
public class NewsController {
    @Autowired
    private NewsService newsService;

    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping("/add")
    public Result addNews(@NotBlank(message = "发布者不能为空") @RequestParam("publisher") String publisher,
                          @NotBlank(message = "内容不能为空") @RequestParam("content") String content,
                          @Length(message = "标题在1-20个字符以内", min = 1, max = 20)@RequestParam("title") String title,
                          @RequestParam("photo") MultipartFile photo){

        NewsRequest newsRequest = new NewsRequest(publisher,content,title,photo);
        return new Result(newsService.addNews(newsRequest), 0, "发布成功");
    }

    // 为求便捷， 附件单独上传
    @PostMapping("/addFile")
    public Result uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("newsId") Integer newsId) {

        String fileName = fileStorageService.storeFile(file);

        // 下载url ---附件
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();

        ;
        return new Result(newsService.addOneFile(fileDownloadUri,fileName,newsId),
                          0,
                          "success");
    }

    @GetMapping("/latestNews")
    public Result getLatestNews(@RequestParam String time){
        List<News> latestNews = newsService.findLatestNews(time);
        return new Result(latestNews, 0, "查找成功");
    }

//    @GetMapping("/recentNews")
//    public Result getRecentNews(@RequestParam(value = "page", defaultValue = "1")Integer page){
//        Sort sort = new Sort(Sort.Direction.DESC, "publishTime");
//        Pageable pageable = PageRequest.of(page -1, 5, sort);
//        Page<News> pages = newsService.findAll(pageable);
//        return new Result(pages, 0, "查找成功");
//    }
    @GetMapping("/recentNews")
    public Result getRecentNews(@RequestParam(value = "pageOffset",defaultValue = "0")Integer pageOffset){
        List<News> pages=newsService.findRecentNews(pageOffset,5);
        return new Result(pages,0,"查找成功");
    }

    // 下载附件
    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        Resource resource = fileStorageService.loadFileAsResource(fileName);
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException e) {
            log.info("Could not determine file type.");
        }
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

}
