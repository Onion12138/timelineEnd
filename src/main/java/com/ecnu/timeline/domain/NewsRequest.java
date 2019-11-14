package com.ecnu.timeline.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @author onion
 * @date 2019/11/2 -6:12 下午
 */
@Data
@AllArgsConstructor
public class NewsRequest implements Serializable {
    private String publisher;
//    @NotEmpty(message = "内容不能为空")
    private String content;
//    @Length(message = "标题在1-20个字符以内", min = 1, max = 20)
    private String title;

    private MultipartFile photo;
}
