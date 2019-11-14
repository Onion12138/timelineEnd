package com.ecnu.timeline.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
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
    private Date publishTime;
    private String content;
    private String title;
    private String fileDownloadUri; // pic url
}
