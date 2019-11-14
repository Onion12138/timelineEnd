package com.ecnu.timeline.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author onion
 * @date 2019/11/1 -7:46 下午
 */
@Data
@Entity
@Accessors(chain = true)
@Table(name = "news")
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String publisher;
    @Column(name = "publish_time")
    private LocalDateTime publishTime;
    private String content;
    private String title;
    // 存储了两次
    private String fileDownloadUri; // 附件
    private String showUrl; // 展示用
}
