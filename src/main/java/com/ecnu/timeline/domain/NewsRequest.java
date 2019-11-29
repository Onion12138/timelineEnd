package com.ecnu.timeline.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @author onion
 * @date 2019/11/2 -6:12 下午
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsRequest implements Serializable {
    private String publisher;
    @NotEmpty(message = "内容不能为空")
    private String content;
    @NotEmpty(message = "标题不能为空")
    private String title;
    private MultipartFile photo;
}
