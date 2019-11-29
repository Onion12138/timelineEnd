package com.ecnu.timeline.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    private String fileDownloadUri; // pic url
    private String showUrl;
}
